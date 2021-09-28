$('.message a').click(function(){
   $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
   setTimeout(function(){
         $('.lang').css("display", "block");
     },650);
});