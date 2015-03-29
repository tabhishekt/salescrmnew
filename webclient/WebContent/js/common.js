var dojoPaths = new Object();
var relPath = "../../";
var userData;

dojoPaths.paths = {
		'lib': relPath + 'lib',
		'gridx': relPath + 'lib/gridx-1.3.6'
};

dojoConfig = { 
		parseOnLoad: true,
		isDebug: true,
		paths: dojoPaths.paths,
		locale: "en",
		async: true
};

window.onload = function() {
	// ensure dojo 'require' function is defined
	if (typeof require == "undefined") {
		alert("Could not load dojo. Please ensure dojo.js lives under Webcontent/js/dojo/");
	}
	
	var cookieValue = getCookie("salescrm");
	if (cookieValue != "") {
		this.userData = JSON.parse(cookieValue);
	}
}

getCookie = function (cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length,c.length);
    }
    return "";
}

logout = function() {
	var expires = "expires=Thu, 01 Jan 1970 00:00:00 UTC";
    document.cookie = "salescrm" + "=" + "{}; " + expires;
    location.reload();
}

getQueryVariable = function(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
            var pair = vars[i].split("=");
            if(pair[0] == variable){return pair[1];}
    }
    
    return undefined;
}