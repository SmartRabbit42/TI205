"use strict";

var ip = location.search.substr(4, location.search.indexOf("?port=") - 4);
var port = location.search.substr(location.search.indexOf("?port=") + 6);

var socket = io.connect("http://" + ip + ":" + port);
var add = true;
var logado = false;
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
    for (var i = 0; i < rooms.length; i++)
        $(".list-group-item")[i].style.backgroundColor = "#374557";
    $(".list-group-item")[id].style.backgroundColor = "#0099cc";
}

function enterRoom(e) {
    var id = roomExists(e.currentTarget.getAttribute('id'));
    if (id != -1) {
        $(".message-flow")[currentRoom].hidden = true;
        $(".vizualizar")[currentRoom].innerHTML = $(".message-flow")[currentRoom].children[0].innerHTML;
        if ($(".vizualizar")[currentRoom].innerHTML == 0)
            $(".vizualizar")[currentRoom].hidden = true;
        else
            $(".vizualizar")[currentRoom].hidden = false;
        $(".message-flow")[currentRoom].children[0].innerHTML = 0;
        botoes(id);
        currentRoom = id;
        $(".message-flow")[currentRoom].hidden = false;
        $(".message-flow")[currentRoom].children[0].innerHTML = $(".vizualizar")[currentRoom].innerHTML;
        $(".vizualizar")[currentRoom].innerHTML = 0;
        $(".vizualizar")[currentRoom].hidden = true;
    }
}

function verMensagens() {
    if (!$($(".message-flow")[currentRoom]).scrollTop() + $($(".message-flow")[currentRoom]).innerHeight() >= $(".message-flow")[currentRoom].scrollHeight) {
        $(".message-flow")[currentRoom].lastChild.scrollIntoView();
        $(".message-flow")[currentRoom].children[0].innerHTML = 0;
        $(".message-flow")[currentRoom].children[0].hidden = true;
    }
}

function createRoom(e) {
    if ($("#create-room-name").val() != "") {
        socket.emit("create", {
            name: $("#create-room-name").val(),
            pass: $("#create-room-pass").val(),
            status: logado
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
                pass: pass,
                status: logado
            });
        }
    } else {
        socket.emit("join", {
            name: a.currentTarget.id,
            pass: "",
            status: logado
        });
        $(this).addChat();
    }
}

$("#enter-room-pass").keydown(function(e) {
    if ((e.which == 13 || e.keycode == 13)) {
        return false;
    }
})

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
    if ($('#sig-password').val() != $('#sig-password-c').val()) {
        $(".r-p-c small").remove();
        $(".r-p-c").append("<small class='text-danger'>As senhas não condizem.");
        valido = false;
    } else {
        $(".r-p-c small").remove();
    }
    if (!valido) {
        return;
    }
    $.post("http://" + ip + ":" + port + "/register", $('#signup-form').serialize(), (data) => {
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

function carregarMensagem() {
    var x = $(".message-flow")[currentRoom].children[1].name;
    console.log($(".message-flow")[currentRoom].children[1].name);
    $($(".message-flow")[currentRoom].children[1]).remove();
    $($(".message-flow")[currentRoom]).prepend("<div class='progress mx-auto mt-1 w-50'><div class='progress-bar progress-bar-striped progress-bar-animated' role='progressbar' aria-valuenow='100' aria-valuemin='0' aria-valuemax='100' style='width: 100%'></div></div>");
    socket.emit("load", {
        room: rooms[currentRoom],
        times: x
    });
}

function Logado(username) {
    $(".navbar-brand").html("Seja bem-vindo, " + username.bold());
    $("#btn-reg").addClass("d-none");
    $("#btn-log").addClass("d-none");
    $("#btn-ext").removeClass("d-none");
    if ($('#saveChats')[0].checked) {
        for (var i = 1; i < rooms.length; i++) {
            socket.emit("cadastro", rooms[i]);
        }
    }
}

function visualizar(x) {
    var messageFlow = $(".message-flow")[currentRoom];
    var element = messageFlow.children[messageFlow.children.length - x];
    var posInfoElement = element.getBoundingClientRect();
    var posinfoChat = $("#chat")[0].getBoundingClientRect();
    if (posInfoElement.y + posInfoElement.height < posinfoChat.y + posinfoChat.height)
        messageFlow.children[0].innerHTML = x - 1;
}

socket.on("joined", function(data) {
    $($(".message-flow")[roomExists(data.room)]).append("<div class='d-flex flex-row'><div class='justify-content-center message text-danger'>" + data.msg + "</div></div>");
});

socket.on("join", function(room) {
    if (currentRoom != -1) {
        $(".message-flow")[currentRoom].hidden = true;
        if ($(".message-flow")[currentRoom].children[0].innerHTML != '0') {
            $(".vizualizar")[currentRoom].hidden = false;
            $(".vizualizar")[currentRoom].innerHTML = $(".message-flow")[currentRoom].children[0].innerHTML;
        }
    }
    currentRoom = rooms.length;
    rooms[currentRoom] = room;
    $("#chat").append("<div class='message-flow' id='message-flow'><button style='bottom: 100px; right: 100px; border-radius: 50%' class='btn btn-danger shadow position-absolute' id='" + currentRoom + "' onclick='verMensagens()' hidden='true'>0</button></div>");
    $("#room-joined .list-group").append("<li class='list-group-item list-group-item-action' id='" + room + "' onclick='enterRoom(event)' style='background-color: #0099cc'><div class='d-flex flex-row justify-content-between'>" + room + "<span class='vizualizar badge badge-danger shadow' hidden='true'>0</span></div></li>");
    botoes(currentRoom);
    if (currentRoom != 0) {
        $($(".message-flow")[currentRoom]).prepend("<div class='progress mx-auto mt-1 w-50'><div class='progress-bar progress-bar-striped progress-bar-animated' role='progressbar' aria-valuenow='100' aria-valuemin='0' aria-valuemax='100' style='width: 100%'></div></div>");
    }
    socket.emit("load", {
        room: room,
        times: 0
    });
});

socket.on("msg", function(data) {
    var thisMessageFlow = $(".message-flow")[roomExists(data.room)];
    thisMessageFlow.innerHTML += data.msg;
    var posInfoMessage = thisMessageFlow.lastChild.getBoundingClientRect();
    var posinfoChat = $("#chat")[0].getBoundingClientRect();
    if (data.room == rooms[currentRoom]) {
        if (posInfoMessage.y + posInfoMessage.height >= posinfoChat.y + posinfoChat.height) {
            thisMessageFlow.children[0].hidden = false;
            thisMessageFlow.children[0].innerHTML = parseInt(thisMessageFlow.children[0].innerHTML) + 1;
        }
    } else {
        thisMessageFlow.children[0].hidden = false;
        $(".vizualizar")[roomExists(data.room)].hidden = false;
        $(".vizualizar")[roomExists(data.room)].innerHTML = parseInt($(".vizualizar")[roomExists(data.room)].innerHTML) + 1;
    }
});

socket.on("correctPass", function(data) {
    if (data.response) {
        $("#enter-room").modal('hide');
        $("#room-created [id='" + data.id + "']").remove();
        if (add == false)
            $(this).addChat();
    } else {
        $("#enter-room .modal-footer .text-danger").remove();
        $("#enter-room .modal-footer").prepend("<div class='h-3 text-danger'>Senha incorreta.")
    }
});

socket.on("logar", function(room) {
    rooms[rooms.length] = room;
    $("#chat").append("<div class='message-flow' id='message-flow'><button style='bottom: 100px; right: 100px; border-radius: 50%' class='btn btn-danger shadow position-absolute' id='" + currentRoom + "' onclick='verMensagens()' hidden='true'>0</button></div>");
    $(".message-flow")[roomExists(room)].hidden = true;
    $("#room-joined .list-group").append("<li class='list-group-item list-group-item-action' id='" + room + "' onclick='enterRoom(event)' style='background-color: #0099cc'><div class='d-flex flex-row justify-content-between'>" + room + "<span class='vizualizar badge badge-danger shadow' hidden='true'>0</span></div></li>");
    $($(".message-flow")[roomExists(room)]).append("<div class='d-flex flex-row'><div class='justify-content-center message text-danger'>Você entrou na sala " + room + "</div></div>");
    $("#room-created [id='" + room + "']").remove();
    socket.emit("load", {
        room: room,
        times: 0
    });
    currentRoom = 0;
});

socket.on("load", function(data) {
    var room = roomExists(data.room);
    $($(".message-flow")[room].getElementsByClassName("progress")[0]).remove();
    var x = $(".message-flow")[room].children[0].outerHTML;
    $($(".message-flow")[room].children[0]).remove();
    var y;
    for (var i = 0; i < data.msgs.length; i++) {
        $($(".message-flow")[room]).prepend(data.msgs[i].content);
    }
    if (data.msgs.length > 0)
        $(".message-flow")[room].children[data.msgs.length - 1].scrollIntoView();
    if (data.msgs.length == 5)
        $($(".message-flow")[room]).prepend("<button class='btn btn-secondary' onclick='carregarMensagem()' name='" + (1 + parseInt(data.x)) + "'>Carregar Mensagens</button>");
    else
        $($(".message-flow")[room]).prepend("<div class='d-flex flex-row'><div class='justify-content-center message text-danger'>Início da conversa</div></div>");
    $($(".message-flow")[room]).prepend(x);
});

socket.on("fim", function(a) {
    botoes(currentRoom);
});

socket.on("login", function(data) {
    if (data.status) {
        username = $("#log-user").val();
        $("#popup-log .modal-footer .h-3").remove();
        $("#popup-log").modal('hide');
        socket.emit("login", username);
        logado = true;
        Logado(username);
        $('#login-form').trigger("reset");

    } else {
        $("#popup-log .modal-footer .text-danger").remove();
        $("#popup-log .modal-footer").prepend("<div class='h-3 text-danger'>Nome de usuário ou senha incorreta.");
        $("#log-password").val("");
    }
});

socket.on("create", function(room) {
    if (currentRoom != -1) {
        if ($(".message-flow")[currentRoom].children[0].innerHTML != 0) {
            $(".vizualizar")[currentRoom].innerHTML = $(".message-flow")[currentRoom].children[0].innerHTML;
            $(".vizualizar")[currentRoom].hidden = false;
        } else {
            $(".vizualizar")[currentRoom].hidden = true;
        }
        $(".message-flow")[currentRoom].children[0].innerHTML = 0;
        $(".message-flow")[currentRoom].hidden = true;
    }
    currentRoom = rooms.length;
    rooms[currentRoom] = room;
    $("#chat").append("<div class='message-flow' id='message-flow'><button style='bottom: 100px; right: 100px; border-radius: 50%' class='btn btn-danger shadow position-absolute' id='" + currentRoom + "' onclick='verMensagens()' hidden='true'>0</button></div>");
    $("#room-joined .list-group").append("<li class='list-group-item list-group-item-action' id='" + room + "' onclick='enterRoom(event)' style='background-color: #0099cc'><div class='d-flex flex-row justify-content-between'>" + room + "<span class='vizualizar badge badge-danger shadow' hidden='true'>0</span></div></li>");
    $($(".message-flow")[currentRoom]).append("<div class='d-flex flex-row'><div class='justify-content-center message text-danger'>Você criou a sala " + room + "</div></div>");
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
    $(function() {
        $('[data-toggle="tooltip"]').tooltip()
    })
    socket.emit("login", username);

    socket.emit("join", {
        name: "global",
        pass: "",
        status: logado
    });

    $(".modal").on("shown.bs.modal", () => {
        $(this).find('[autofocus]').focus();
    })

    $("#enter-room").on("show.bs.modal", function(e) {
        $("#enter-room .modal-title").html(e.relatedTarget.id);
    })

    $("#chat").scroll(function() {
        var x = parseInt($(".message-flow")[currentRoom].children[0].innerHTML);
        if (x > 0)
            visualizar(x);
        else $(".message-flow")[currentRoom].children[0].hidden = true;
    });

    $("#room").click(function() {
        $(".message-flow")[currentRoom].hidden = true;
        currentRoom = this.id;
        $(".message-flow")[currentRoom].hidden = false;
    });

    $("#message-input").keypress(function(e) {
        if ((e.which == 13 || e.keycode == 13) && $("#message-input").val() != "") {
            $(this).message("t", $("#message-input").val());
            $(this).val("");
        }
    });

    $("input[type=file]").change(function(e) {
        var file = document.querySelector('input[type=file]').files[0];
        var extension = $('input[type=file]').val().replace(/^.*\./, '').toLowerCase();
        switch (extension) {
            default:
                alert("Formato não suportado.");
                break;
            case "png":
            case "jpg":
            case "jpeg":
            case "gif":
                $(this).message("i", file);
                break;
            case "mp4":
                $(this).message("v", file, extension);
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

$("#search-chat").keyup(function(e) {
    if (add) {
        if ($("#search-chat").val() != "")
            for (var i = 0; i < $("#room-joined .list-group")[0].children.length; i++) {
                if (!$("#room-joined .list-group")[0].children[i].children[0].innerHTML.includes($("#search-chat").val()))
                    $("#room-joined .list-group")[0].children[i].hidden = true;
                else
                    $("#room-joined .list-group")[0].children[i].hidden = false;
            }
        else
            for (var i = 0; i < $("#room-joined .list-group")[0].children.length; i++)
                $("#room-joined .list-group")[0].children[i].hidden = false;
    } else {
        if ($("#search-chat").val() != "")
            for (var i = 0; i < $("#room-created .list-group")[0].children.length; i++) {
                if (!$("#room-created .list-group")[0].children[i].children[0].innerHTML.includes($("#search-chat").val()))
                    $("#room-created .list-group")[0].children[i].hidden = true;
                else
                    $("#room-created .list-group")[0].children[i].hidden = false;
            }
        else
            for (var i = 0; i < $("#room-created .list-group")[0].children.length; i++)
                $("#room-created .list-group")[0].children[i].hidden = false;
    }
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
            $("#add-chat").css("background-color", "#38D159");
            $("#add-chat").css("border-color", "#38D159");
        } else {
            $("#chat-count").removeClass("add-chat-box");
            $("#room-created").addClass("d-none");
            $("#chat-count").one('transitionend', function() {
                add = true;
                $("#room-joined").removeClass("d-none");
            });
            $("#add-chat").css("background-color", "#24292e");
            $("#add-chat").css("border-color", "#24292e");
        }
    }
});

jQuery.fn.extend({
    message: function(a, content, extension = null) {
        $(document.getElementsByClassName("message-flow")[currentRoom]).append("<div class='d-flex flex-row nvista' style='border-color:#24292e !important'>");
        $(document.getElementsByClassName("message-flow")[currentRoom].lastChild).last().append("<div class='justify-content-start message'>");
        $(document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild).last().append("<small class='d-flex flex-row justify-content-start'>");
        $(document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild.lastChild).last().append(username + "&nbsp•&nbsp" + $(this).getTime().bold());
        switch (a) {
            case "t":
                $(document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild).last().append(content);
                socket.emit("msg", {
                    msg: document.getElementsByClassName("message-flow")[currentRoom].lastChild.outerHTML,
                    room: rooms[currentRoom],
                    logado: logado,
                    type: "t"
                });
                break;
            case "i":
                $(document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild).append("<img class='img' src=''>");
                fileHandler(content);
                $(document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild.lastChild).last().on("load", function() {
                    socket.emit("msg", {
                        msg: document.getElementsByClassName("message-flow")[currentRoom].lastChild.outerHTML,
                        room: rooms[currentRoom],
                        logado: logado,
                        type: "i"
                    });
                    $("#chat").scrollTop($($(".message-flow")[currentRoom]).height());
                });
                break;
            case "v":
                $(document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild).append("<video class='vid' controls data-toggle='tooltip' data-placement='right' title='vídeos não são salvos, aproveite-os bem ;)'>");
                fileHandler(content);
                $(document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild.lastChild).tooltip();
                document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild.lastChild.onloadeddata = function() {
                    socket.emit("msg", {
                        msg: document.getElementsByClassName("message-flow")[currentRoom].lastChild.outerHTML,
                        room: rooms[currentRoom],
                        logado: logado,
                        type: "v"
                    });
                    $("#chat").scrollTop($(".message-flow").height());
                };
                break;
            case "a":
                $(document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild).append("<audio class='aud' controls data-toggle='tooltip' data-placement='right' title='áudios não são salvos, aproveite-os bem ;)'>");
                fileHandler(content);
                $(document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild.lastChild).tooltip();
                document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild.lastChild.onloadeddata = function() {
                    socket.emit("msg", {
                        msg: document.getElementsByClassName("message-flow")[currentRoom].lastChild.outerHTML,
                        room: rooms[currentRoom],
                        logado: logado,
                        type: "a"
                    });
                    $("#chat").scrollTop($(".message-flow")[currentRoom].lastChild.scrollHeight);
                };
        }
        $("#chat").scrollTop($($(".message-flow")[currentRoom]).height());
        $(".message-flow")[currentRoom].children[0].innerHTML = 0;
    }
});

function fileHandler(file) {
    var reader = new FileReader();
    reader.onload = function(evt) {
        $(document.getElementsByClassName("message-flow")[currentRoom].lastChild.lastChild.lastChild).attr("src", evt.target.result);
    }
    reader.readAsDataURL(file);
}

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