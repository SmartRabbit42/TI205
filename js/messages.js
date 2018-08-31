var me = true; //Variáveis usadas
var lastme = false; //para testes;

$(document).ready(function() {


    $("#message-input").keypress(function(e) {
        if ((e.which == 13 || e.keycode == 13) && $("#message-input").val() != "") {
            if (me == true) {
                if (lastme == true) {
                    $(".message").last().removeClass("tail")
                    $("#message-flow").append("<div class='d-flex flex-row justify-content-end'><div class='message out tail text shadow'>" + $("#message-input").val() + "</div></div>")
                } else {
                    $("#message-flow").append("<div class='d-flex flex-row justify-content-end'><div class='message out head tail text shadow'>" + $("#message-input").val() + "</div></div>")
                    lastme = true;
                }
            } else {
                if (lastme == true) {
                    $("#message-flow").append("<div class='d-flex flex-row justify-content-start'><div class='message in head tail text shadow'>" + $("#message-input").val() + "</div></div>")
                    lastme = false;
                } else {
                    $(".message").last().removeClass("tail")
                    $("#message-flow").append("<div class='d-flex flex-row justify-content-start'><div class='message in tail text shadow'>" + $("#message-input").val() + "</div></div>")
                }
            }
            $("#chat").scrollTop($("#message-flow").height());
        }
    });

    $("#me").click(function() {
        me = !me;
    });

    $("input[type=file]").change(function() {
        if (me == true) {
            if (lastme == true) {
                $(".message").last().removeClass("tail")
                $("#message-flow").append("<div class='d-flex flex-row justify-content-end'><div class='message out tail shadow'><img class='img' src='' ></div></div>")
            } else {
                $("#message-flow").append("<div class='d-flex flex-row justify-content-end'><div class='message out head tail shadow'><img class='img' src='' ></div></div>");
                lastme = true;
            }
        } else {
            if (lastme == true) {
                $("#message-flow").append("<div class='d-flex flex-row justify-content-start'><div class='message in head tail shadow'><img class='img src='' ></div></div>")
                lastme = false;
            } else {
                $(".message").last().removeClass("tail")
                $("#message-flow").append("<div class='d-flex flex-row justify-content-start'><div class='message in tail shadow'><img class='img src='' ></div></div>")
            }

        }
        var file = document.querySelector('input[type=file]').files[0];
        var reader = new FileReader();
        reader.onloadend = function() {
            $(".img").last().attr("src", reader.result);
        }
        if (file) {
            reader.readAsDataURL(file);
        } else {
            $(".img").last().attr("src", "");
        }
        $("input[type=file]").val("");
        $(".img").on('load', function() {
            $("#chat").scrollTop($("#message-flow").height());

        });
    })

});

(function($) {
    $.fn.displayMessage = function() {

        return this;
    };
})(jQuery);