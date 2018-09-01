var me = true; //Variáveis usadas
var lastme = false; //para testes;

$(document).ready(function() {


    $("#message-input").keypress(function(e) {
        if ((e.which == 13 || e.keycode == 13) && $("#message-input").val() != "") {
            $(this).displayMessage("t", $("#message-input").val());
        }
    });

    $("#me").click(function() {
        me = !me;
    });

    $("input[type=file]").change(function() {
        var file = document.querySelector('input[type=file]').files[0];
        $(this).displayMessage("i", file);
        $(this).val("");
    })

});

jQuery.fn.extend({
    displayMessage: function(a, content) {
        $("#message-flow").append("<div class='d-flex flex-row'>");
        $("#message-flow .flex-row").last().append("<div class='message shadow'>");
        if (me === true) {
            $("#message-flow .flex-row").last().addClass("justify-content-end");
            if (lastme === true) {
                $(".message").eq(-2).removeClass("tail");
                $(".message").last().addClass("message out tail text");
            } else {
                $(".message").last().addClass("message head out tail");
                lastme = true;
            }
        } else {
            $("#message-flow .flex-row").last().addClass("justify-content-start");
            if (lastme === true) {
                $(".message").last().addClass("message in head tail");
                lastme = false;
            } else {
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
                File.imageConver(content);
                $(".message .img").on("load", function() {
                    $("#chat").scrollTop($("#message-flow").height());
                });
        }
        $(".message").last().append("<small class='d-flex flex-row justify-content-end p-1'>");
        $(".message small").last().append($(this).getTime());
        $("#chat").scrollTop($("#message-flow").height());
    }
});


File = {
    imageConver: function(file) {
        var reader = new FileReader();
        reader.onloadend = function() {
            $(".message .img").last().attr("src", reader.result);
        }
        reader.readAsDataURL(file);
    }
};

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