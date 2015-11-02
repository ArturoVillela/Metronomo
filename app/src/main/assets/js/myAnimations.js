	TweenMax.from("#logotipo",2, {scale:0.5,x:-30,y:-20, ease: Elastic.easeOut.config(1, 0.3)});
	
	forward();
	
	function forward(){
		TweenLite.to("#logo2", 2, {rotation:-150,  ease: Elastic.easeOut.config(1, 0.3), transformOrigin:"30% 10%",delay:2});
		TweenLite.to("#logo2", 0.5, {rotation:0,  transformOrigin:"30% 10%",delay:5});
	}