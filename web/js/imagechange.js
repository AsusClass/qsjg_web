var i = 0;
var timer;
$(function() {
	$("img").eq(0).show().siblings().hide();
	start();
	$("b").hover(function() {
		clearInterval(timer);
		i = $(this).index();
		change()
	}, function() {
		start()
	});
	$(".left").click(function() {
		i--;
		if(i == -1) {
			i = 5
		}
		change()
	});
	$(".right").click(function() {
		i++;
		if(i == 6) {
			i = 0
		}
		change()
	})
});

function start() {
	timer = setInterval(function() {
		i++;		
		if(i == 6) {
			i = 0
		}
		change()
	}, 6000)
}

function change() {
	
	$("img").eq(i).fadeIn(100).siblings().stop(true, true).fadeOut(10000);
	sleep(10000000);
		console.log('’')
	$("b").eq(i).addClass("current").siblings().removeClass("current")
};

function sleep(numberMillis) {
	var now = new Date();
	var exitTime = now.getTime() + numberMillis;
	while(true) {
		now = new Date();
		if(now.getTime() > exitTime)
			return;
	}
}