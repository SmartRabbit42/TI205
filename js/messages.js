"use strict";


var socket = io.connect("http://localhost:3000");
var add = true;
var rooms = [];
var currentRoom = -1;

var ID = function() {
  return '_' + Math.random().toString(36).substr(2, 9).toUpperCase();
};
var username = "ANOM" + ID();

function roomExists(room) {
  for (var i = 0; i < rooms.length; i++)
    if (rooms[i] == room)
      return i;
  return -1;
}

function botoes(id) {
  for (var i = 0; i < rooms.length; i++) {
    $(".list-group-item")[i].style.backgroundColor = "#374557";
    $(".list-group-item")[id].style.backgroundColor = "#0099cc";
  }
}
//Login
function enterRoom(e) {
  var id = roomExists(e.currentTarget.getAttribute('id'));
  console.log(id);
  if (id != -1) {
    $(".message-flow")[currentRoom].hidden = true;
    botoes(id);
    currentRoom = id;
    $(".message-flow")[currentRoom].hidden = false;
  }
}

function createRoom(e) {
  if ($("#create-room-name").val() != "") {
    socket.emit("create", {
      name: $("#create-room-name").val(),
      pass: $("#create-room-pass").val()
    });
  } else {
    $(".c-n").append("<small class='text-danger'>Dê um nome à sua sala.");
  }
}

function joinRoom(a) {
  if (a == true) {
    var pass = $("#enter-room-pass").val();
    if (pass != "") {
      socket.emit("join", {
        name: $("#enter-room .modal-title").html(),
        pass: pass
      });
    }
  } else {
    socket.emit("join", {
      name: a.currentTarget.id,
      pass: ""
    });
    a.currentTarget.remove();
    $(this).addChat();
  }
}

function Register() {
  var valido = true;
  const name = new RegExp('^[A-Za-z]{3}.{0,17}');
  const pass = new RegExp('.{8,30}')
  if (!name.test($('#sig-user').val())) {
    $(".r-n small").remove();
    $(".r-n").append("<small class='text-danger'>Seu nome de usuário deve conter, no mínimo, 3 letras iniciais, seguidas ou não por outros 17 caracteres quaisquer.");
    valido = false;
  } else {
    $(".r-n small").remove();
  }
  if (!pass.test($('#sig-password').val())) {
    $(".r-p small").remove();
    $(".r-p").append("<small class='text-danger'>Sua senha deve conter, no mínimo, 8 letras, números ou <i>underlines</i>, sendo o máximo 30.");
    valido = false;
  } else {
    $(".r-p small").remove();
  }
  if (!valido) {
    return;
  }
  $.post("http://localhost:3000/register", $('#signup-form').serialize(), (data) => {
    if (data.signup) {
      $("#popup-reg .modal-body").html("<div class='h-2 text-success'>Registrado!");
      setTimeout(function() {
        $("#popup-reg").modal('hide');
      }, 1500);
      $('#signup-form').trigger("reset");
    } else {
      $(".r-n small").remove();
      $(".r-n").append("<small class='text-danger'>Nome de usuário já em uso.");
    }
  });
}

function Login() {
  socket.emit("logar", {
    name: $("#log-user").val(),
    pass: $("#log-password").val()
  });
}

function Logado(username) {
  $(".navbar-brand").html("Seja bem-vindo, " + username.bold());
  $("#btn-reg").addClass("d-none");
  $("#btn-log").addClass("d-none");
  $("#btn-ext").removeClass("d-none");
}
//Socket
socket.on("joined", function(data) {
  $($(".message-flow")[roomExists(data.room)]).append("<div style='color: red'>" + data.msg + "</div>");
});
socket.on("join", function(room) {
  if (currentRoom != -1)
    $(".message-flow")[currentRoom].hidden = true;
  currentRoom = rooms.length;
  rooms[currentRoom] = room;
  $("#chat").append("<div class='message-flow' id='message-flow'></div>");
  $("#room-joined .list-group").append("<li  class='list-group-item list-group-item-action' id='" + room + "' onclick='enterRoom(event)' style='background-color: #0099cc'><div class='d-flex flex-row justify-content-center'>" + room + "</div></li>");
  botoes(currentRoom);
});
socket.on("msg", function(data) {
  $(".message-flow")[findRoom(data.room)].innerHTML += data.msg;
});
socket.on("correctPass", function(data) {
  console.log(data);
  if (data.response) {
    $("#enter-room").modal('hide');
    $("#room-created #" + data.id).remove();
    if (add == false)
      $(this).addChat();
  } else {
    $("#enter-room .modal-footer .text-danger").remove();
    $("#enter-room .modal-footer").prepend("<div class='h-3 text-danger'>Senha incorreta.")
  }
});
socket.on("login", function(response) {
  if (response) {
    username = $("#log-user").val();
    Logado(username);
    $("#popup-log .modal-footer .h-3").remove();
    $('#login-form').trigger("reset");
    $("#popup-log").modal('hide');
  } else {
    $("#popup-log .modal-footer .text-danger").remove();
    $("#popup-log .modal-footer").prepend("<div class='h-3 text-danger'>Nome de usuário ou senha incorreta.");
    $("#log-password").val("");
  }
});

socket.on("create", function(room) {
  if (currentRoom != -1) {
    $(".message-flow")[currentRoom].hidden = true;
  }
  currentRoom = rooms.length;
  rooms[currentRoom] = room;
  $("#chat").append("<div class='message-flow' id='message-flow'></div>");
  $("#room-joined .list-group").append("<li class='list-group-item list-group-item-action' id='" + room + "' onclick='enterRoom(event)'><div class='d-flex flex-row justify-content-center'>" + room + "</div></li>");
  $($(".message-flow")[currentRoom]).append("<div class='d-flex flex-row'><div class='justify-content-center message'  style='color: red !important'>Você criou a sala " + room + "</div></div>");
  $("#create-room").modal('hide');
  $(this).addChat();
  botoes(currentRoom);
});

socket.on("created", function(data) {
  if (data.pass) {
    $("#room-created .list-group").append("<li class='list-group-item list-group-item-action' id='" + data.name + "' data-toggle='modal' data-target='#enter-room' style='background-color: #18448e'><div class='d-flex flex-row justify-content-center'>" + data.name + "</div></li>");
  } else
    $("#room-created .list-group").append("<li class='list-group-item list-group-item-action' id='" + data.name + "' onclick='joinRoom(event)' style='background-color: #18448e'><div class='d-flex flex-row justify-content-center' >" + data.name + "</div></li>");
});

$(document).ready(function() {
  socket.emit("login", username);
  socket.emit("join", {
    name: "global",
    pass: ""
  });

  $("#enter-room").on("show.bs.modal", function(e) {
    console.log(e.relatedTarget.id);
    $("#enter-room .modal-title").html(e.relatedTarget.id);
  })

  $("#create-room").submit(function(e) {
    var roominfo = {
      name: $("#room-name").val(),
      pass: $("#room-pass").val()
    };
    socket.emit("create", roominfo);
    e.preventDefault();
  });
  $("#room").click(function() {
    $(".message-flow")[currentRoom].hidden = true;
    currentRoom = this.id;
    $(".message-flow")[currentRoom].hidden = false;
  });
  $("#join-room").submit(function() {
    var roominfo = {
      name: $("#room-name").val(),
      pass: $("#room-pass").val()
    };
    socket.emit("join", roominfo);
  });
  $("#message-input").keypress(function(e) {
    if ((e.which == 13 || e.keycode == 13) && $("#message-input").val() != "") {
      $(this).message("t", $("#message-input").val());
      $(this).val("");
    }
  });
  $("input[type=file]").change(function() {
    var file = document.querySelector('input[type=file]').files[0];
    var extension = $('input[type=file]').val().replace(/^.*\./, '').toLowerCase();
    switch (extension) {
      case "png":
      case "jpg":
      case "jpeg":
      case "gif":
        $(this).message("i", file);
        break;
      case "mp4":
        $(this).message("v", file, extension);
        break;
      case "wmv":
        alert("Formato de vídeo não suportado.");
        break;
      case "mp3":
      case "ogg":
        $(this).message("a", file, extension);
        break;
    }
    $(this).val("");
  })

  $("#add-chat").click($(this).addChat);

  $('.modal').on('hidden.bs.modal', function() {
    $('.modal form').trigger("reset");
    $('.modal .text-danger').remove();

  })

});

jQuery.fn.extend({
  addChat: function() {
    if (add) {
      $("#room-joined").addClass("d-none");
      $("#chat-count").addClass("add-chat-box");
      $(this).off("click");
      $("#chat-count").one('transitionend', function() {
        $('#room-created').removeClass("d-none");
        add = false;
        $("#add-chat").click($(this).addChat);
      });
      $(this).css("background-color", "#38D159");
      $(this).css("border-color", "#38D159");
    } else {
      $("#chat-count").removeClass("add-chat-box");
      $("#room-created").addClass("d-none");
      $("#chat-count").one('transitionend', function() {
        add = true;
        $("#room-joined").removeClass("d-none");
      });
      $(this).css("background-color", "#24292e");
      $(this).css("border-color", "#24292e");
    }
  }
})

jQuery.fn.extend({
  message: function(a, content, extension = null) {
    $(document.getElementsByClassName("message-flow")[currentRoom]).append("<div class='d-flex flex-row' style='border-color:#24292e !important'>");
    $(document.getElementsByClassName("message-flow")[currentRoom].lastChild).eq(-3).addClass("border-bottom");
    $(document.getElementsByClassName("message-flow")[currentRoom].lastChild).last().append("<div class='justify-content-start message'>");
    $(document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild).last().append("<small class='d-flex flex-row justify-content-start'>");
    $(document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild.lastChild).last().append(username + "&nbsp•&nbsp" + $(this).getTime().bold());
    switch (a) {
      case "t":

        $(document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild).last().append(content);
        break;
      case "i":
        $(".message").last().append("<img class='img' src=''>");
        $(this).imageConversion(content);
        $(".message .img").on("load", function() {
          $("#chat").scrollTop($(".message-flow").height());
        });
        break;
      case "v":
        $(".message").last().append("<video class='vid' controls>");
        $("video").last().append("<source class='vidsrc' src='' type='video/" + extension + "'>");
        $(this).videoConversion(content);
        $(".message video").on("load", function() {
          $("#chat").scrollTop($(".message-flow").height());
        });
        break;
      case "a":
        $(".message").last().append("<audio class='.aud' controls>");
        $("audio").last().append("<source class='audsrc' src='' type='audio/" + extension + "'>");
        $(this).audioConversion(content);
        $(".message audio").on("load", function() {
          $("#chat").scrollTop($(".message-flow")[currentRoom].lastChild.scrollHeight);
        });
    }
    $("#chat").scrollTop($($(".message-flow")[currentRoom]).height());

    var data = {
      msg: document.getElementsByClassName("message-flow")[currentRoom].lastChild.outerHTML,
      room: rooms[currentRoom]
    };

    socket.emit("msg", data);
  }
});

jQuery.fn.extend({
  imageConversion: function(file) {
    $(".message .img").last().attr("src", URL.createObjectURL(file));

  }
})

jQuery.fn.extend({
  videoConversion: function(file) {
    $('.vidsrc').last().attr("src", URL.createObjectURL(file)).parent()[0].load();
  }
})


jQuery.fn.extend({
  audioConversion: function(file) {
    $('.audsrc').last().attr("src", URL.createObjectURL(file)).onend = function(e) {
      URL.revokeObjectURL(this.src);
    }
  }
})

jQuery.fn.extend({
  getTime: function() {
    var d = new Date($.now());
    var m = d.getMinutes();
    var h = d.getHours();
    if (h < 10) {
      h = "0" + h;
    }
    if (m < 10) {
      m = "0" + m;
    }
    return h + ":" + m;
  }
});

function findRoom(room) {
  for (var i = 0; i < rooms.length; i++)
    if (rooms[i] == room)
      return i;
  return -1;
}