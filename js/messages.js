var add = true;

var isChatEmpty = true;
var isMe = true;

$(document).ready(function() {


    $("#message-input").keypress(function(e) {
        if ((e.which == 13 || e.keycode == 13) && $("#message-input").val() != "") {
            $(this).message("t", $("#message-input").val());
        }
    });

    $("#me").click(function() {
        isMe = !isMe;
    });

    $("input[type=file]").change(function() {
        var file = document.querySelector('input[type=file]').files[0];
        var extension = $('input[type=file]').val().replace(/^.*\./, '').toLowerCase();
        switch (extension) {
            case "png":
            case "jpg":
            case "jpeg":
                $(this).message("i", file);
                break;
            case "mp4":
                $(this).message("v", file, extension);
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
                $("#chat-count ol").append("<li class='list-group-item'>");

                ////////////////////////////////////////////////////////

                $("#chat-count ol li").first().css("background-color", "#38d159").append("<div class='d-flex flex-row justify-content-between'>");
                $("#chat-count ol li .flex-row").first().append("Nova conversa").append("<svg xmlns='http://www.w3.org/2000/svg' viewBox='2 2 20 20' width='24' height='24'><path style='fill:white' d='M17 11a1 1 0 0 1 0 2h-4v4a1 1 0 0 1-2 0v-4H7a1 1 0 0 1 0-2h4V7a1 1 0 0 1 2 0v4h4z' /></svg>")

                ////////////////////////////////////////////////////////

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

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

jQuery.fn.extend({
    message: function(a, content, extension = null) {
        $("#message-flow").append("<div class='d-flex flex-row'>");
        $("#message-flow .flex-row").last().append("<div class='message '>");
        if (isMe) {
            $("#message-flow .flex-row").last().addClass("justify-content-end");
            if ($(".message").eq(-2).hasClass("out")) {
                $(".message").eq(-2).removeClass("tail");
                $(".message").last().addClass("message out tail text");
            } else if ($(".message").eq(-2).hasClass("in") || isChatEmpty) {
                $(".message").last().addClass("message head out tail");
                isChatEmpty = false;
            }
        } else {
            $("#message-flow .flex-row").last().addClass("justify-content-start");
            if ($(".message").eq(-2).hasClass("out") || isChatEmpty) {
                $(".message").last().addClass("message in head tail");
                isChatEmpty = false;
            } else if ($(".message").eq(-2).hasClass("in")) {
                $(".message").eq(-2).removeClass("tail");
                $(".message").last().addClass("message in tail");
            }
        }
        switch (a) {
            case "t":
                $(".message").last().addClass("text").append(content);
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
                $(".message .vid").on("load", function() {
                    $("#chat").scrollTop($("#message-flow").height());
                });
                break;
        }
        $(".message").last().append("<small class='d-flex flex-row justify-content-end p-1'>");
        $(".message small").last().append($(this).getTime());
        $("#chat").scrollTop($("#message-flow").height());
    }
});

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

jQuery.fn.extend({
    imageConversion: function(file) {
        var reader = new FileReader();
        reader.onloadend = function() {
            $(".message .img").last().attr("src", reader.result);
        }
        reader.readAsDataURL(file);
    }
})

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

jQuery.fn.extend({
    videoConversion: function(file) {
        var $source = $('.vidsrc').last();
        $source[0].src = URL.createObjectURL(file);
        $source.parent()[0].load();
    }
})

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

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

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////