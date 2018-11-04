"use strict";

var add = true;

var ID = function() {
    return '_' + Math.random().toString(36).substr(2, 9).toUpperCase();
};

var username = "ANOM" + ID();

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
            $("#popup-reg .modal-footer").prepend("<div class='h-3 text-success'>Registrado!")
            setTimeout(function() { $("#popup-reg").modal('hide') }, 2500);
            $('#signup-form').trigger("reset");
        } else {
            $(".r-n").append("<small class='text-danger'>Nome de usuário já em uso.");
        }
    });
}

function Login() {
    $.post("http://localhost:3000/login", $('#login-form').serialize(), (data) => {
        if (data.login) {
        	username = $("#log-user").val();
            Logado(username);
            $("#popup-log .modal-footer .h-3").remove();
            $('#login-form').trigger("reset");
            $("#popup-log").modal('hide');
        } else {
        	$("#popup-log .modal-footer").prepend("<div class='h-3 text-danger'>Nome de usuário ou senha incorreta.")
            $("#log-password").val("");
        }
    });
}

function Logado(username) {
	$(".navbar-brand").html("Seja bem-vindo, " + username.bold());
	$("#btn-reg").addClass("d-none");
	$("#btn-log").addClass("d-none");
	$("#btn-ext").removeClass("d-none");
}

$(document).ready(function() {
    $("#message-input").keypress(function(e) {
        if ((e.which == 13 || e.keycode == 13) && $("#message-input").val() != "") {
            $(this).message("t", $("#message-input").val());
            $(this).val("");
        }
    });

    $("input[type=file]").change(function() {
        var file = document.querySelector('input[type=file]').files[0];
        $.post("http://localhost:3000/media", $('#file-form').serialize(), (data) => alert(data));
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
});

jQuery.fn.extend({
    addChat: function() {
        if (add) {
            $("#chat-count").children().remove();
            $("#chat-count").addClass("add-chat-box");
            $(this).off("click");
            $("#chat-count").one('transitionend', function() {
                $("#chat-count").append("<ol class='list-group'>");
                $("#chat-count ol").append("<li class='list-group-item btn'>");
                $("#chat-count ol li").first().css("background-color", "#38d159").append("<div class='d-flex flex-row justify-content-between'>");
                $("#chat-count ol li .flex-row").first().append("Nova conversa").append("<svg xmlns='http://www.w3.org/2000/svg' viewBox='2 2 20 20' width='24' height='24'><path style='fill:white' d='M17 11a1 1 0 0 1 0 2h-4v4a1 1 0 0 1-2 0v-4H7a1 1 0 0 1 0-2h4V7a1 1 0 0 1 2 0v4h4z' /></svg>")
                add = false;
                $("#add-chat").click($(this).addChat);
            });
            $(this).css("background-color", "#38D159");
            $(this).css("border-color", "#38D159");
        } else {
            $("#chat-count").removeClass("add-chat-box");
            $("#chat-count").children().remove();
            $("#chat-count").one('transitionend', function() {
                add = true;
            });
            $(this).css("background-color", "#24292e");
            $(this).css("border-color", "#24292e");
        }
    }
})

jQuery.fn.extend({
    message: function(a, content, extension = null) {
        $("#message-flow").append("<div class='d-flex flex-row' style='border-color:#24292e !important'>");
        $("#message-flow .flex-row").eq(-3).addClass("border-bottom");
        $("#message-flow .flex-row").last().append("<div class='justify-content-start message'>");
        $(".message").last().append("<small class='d-flex flex-row justify-content-start h-5'>");
        $(".message small").last().append(username + "&nbsp•&nbsp" + $(this).getTime().bold());
        switch (a) {
            case "t":
                $(".message").last().append(content);
                break;
            case "i":
                $(".message").last().append("<img class='img' src=''>");
                $(this).imageConversion(content);
                $(".message .img").on("load", function() {
                    $("#chat").scrollTop($("#message-flow").height());
                });
                break;
            case "v":
                $(".message").last().append("<video class='vid' controls>");
                $("video").last().append("<source class='vidsrc' src='' type='video/" + extension + "'>");
                $(this).videoConversion(content);
                $(".message video").on("load", function() {
                    $("#chat").scrollTop($("#message-flow").height());
                });
                break;
            case "a":
                $(".message").last().append("<audio class='.aud' controls>");
                $("audio").last().append("<source class='audsrc' src='' type='audio/" + extension + "'>");
                $(this).audioConversion(content);
                $(".message audio").on("load", function() {
                    $("#chat").scrollTop($("#message-flow").height());
                });
        }
        $("#chat").scrollTop($("#message-flow").height());
    }
});

jQuery.fn.extend({
    imageConversion: function(file) {
        $(".message .img").last().attr("src", URL.createObjectURL(file));
        $.post("http://localhost:3000/media", $("#file-form").serialize());
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