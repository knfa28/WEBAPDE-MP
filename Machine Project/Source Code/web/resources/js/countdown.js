// datetime = {years: 0, months:0, days:0, minutes:0, seconds:0}
// JSON format 2015/03/12 01:40 or yyyy/mm/dd hh:mm
String.prototype.toHHMMSS = function () {
    var sec_num = parseInt(this, 10); // don't forget the second param
    var days = Math.floor(sec_num / 3600 / 24);
    var hours   = Math.floor(sec_num / 3600 % 24);
    var minutes = Math.floor((sec_num - (Math.floor(sec_num / 3600) * 3600)) / 60);
    var seconds = sec_num - (Math.floor(sec_num / 3600) * 3600) - (minutes * 60);

    //if (hours   < 10) {hours   = "0"+hours;}
    //if (minutes < 10) {minutes = "0"+minutes;}
    //if (seconds < 10) {seconds = "0"+seconds;}
   // var time    = hours+':'+minutes+':'+seconds;
    var time = seconds + " secs"
    time = (minutes != 0) ? (minutes + " mins, ") + time : time;
    time = (hours != 0) ? (hours + " hrs, ") + time : time;
    time = (days != 0) ? (days + " days, ") + time : time;
    return time;
}

function Countdown(component, datetime) {
	var instance = this;
	var timer;
	var totalSeconds = convertToSeconds();
	
	function convertToSeconds() {
		var today = new Date();
		var deadline = new Date(datetime.slice(0,4), // year
								datetime.slice(5,7) - 1, // month
								datetime.slice(8,10), // day
								datetime.slice(11,13), // hour
								datetime.slice(14,16));
		
		return Math.trunc((deadline.valueOf() - today.valueOf()) / 1000);
	}
	
	function decrement() {
		if (totalSeconds <= 0) {
			this.stop();
			component.html("<b>" + "TASK IS DUE" + "</b>");
			this.sendEmail();
		} else {
			update();
			--totalSeconds;
		}
	}
	
	function update() {
		var output = totalSeconds + "";
		component.html("<b>" + output.toHHMMSS() + "</b>"); // put output timer here
	}
	
	this.start = function() {
		clearInterval(timer);
		timer = 0;
		timer = setInterval(decrement, 1000);
	}
	
	this.stop = function() {
		 clearInterval(timer);
	}
	
	this.sendEmail = function() {
		
	}
}