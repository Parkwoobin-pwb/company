// menu
$(function(){
	// ie7 alert
	if($(document).find(".oldBrowserAlert").length > 0) {
		$(".oldBrowserAlert").animate({"top": 0}, 1000);
		
		$(".oldBrowserAlert .btClose").one("click", function(evt) {
			evt.preventDefault();
			$(".oldBrowserAlert").animate({"top": "-100px"}, 1000);
		});
	}

	// gnb
	navi.init();
	
	if($(document).find("table").length > 0) {
		$("th:empty").html("&nbsp;");
		$("td:empty").html("&nbsp;");
	}


	// select
	$(".selectbox select").bind("change", function(evt) {
		var text = $(this).find("option:selected").text();
		$(this).siblings("a").text(text);
	});

	// placeholder
	placeholder.init(".txtHelp");

	// 파일 업로드
	$(".fileHidden").bind("change", function(evt) {
		
		var file = $(this).val().split(/(\\|\/)/g).pop();
		var ext = file.split(".").pop();
		
		var fileCheck = $(this).val();
		
		var fileSize = this.files[0].size;
		var maxSize = 1024 * 1024 * 300; // 300MB
		
		if(fileSize > maxSize){
			alert("300MB 이하의 파일 사용 가능합니다.");
			$(this).val(null);
			$(this).siblings(".fileName").text("선택된 파일 없음.");
			return;
		}
		
		if("franchiseFileMap" == $(this).attr("id")){
			if(!fileCheck){
				$("#franchiseFileMap").val(null);
				$(this).siblings(".fileName").text("선택된 파일 없음.");
				return false;
			} else if (-1 == file.indexOf(".mp4")){
				$("#franchiseFileMap").val(null);
				$(this).siblings(".fileName").text("선택된 파일 없음.");
				alert("파일 형식이 맞지 않습니다.(mp4 가능)");
				return false;
			} else {
				$("#movie_real_name").val(file);
			}
		}
		
		if("franchiseFileMap" == $(this).attr("class")){
			if(!fileCheck){
				$("#franchiseFileMap").val(null);
				$(this).siblings(".fileName").text("선택된 파일 없음.");
				return false;
			} else if (-1 == file.indexOf(".mp4")){
				$("#franchiseFileMap").val(null);
				$(this).siblings(".fileName").text("선택된 파일 없음.");
				alert("파일 형식이 맞지 않습니다.(mp4 가능)");
				return false;
			} else {
				$("#movie_real_name").val(file);
			}
		}
		
		if($(this).attr("class").includes("franchiseImageFileMap")){
			if(!fileCheck){
				var viewArea = $(this).closest("td").next().children("div").attr("id");
				var preview = document.getElementById("prev_" + viewArea);
				
				preview.remove();
				
				$(this).val(null);
				$(this).siblings(".fileName").text("선택된 파일 없음.");
				return false;
			} else if (-1 == file.indexOf(".jpg") && -1 == file.indexOf(".JPG") && -1 == file.indexOf(".png") && -1 == file.indexOf(".PNG")
					&& -1 == file.indexOf(".jpeg") && -1 == file.indexOf(".JPEG")){
				alert("파일 형식이 맞지 않습니다.(jpg, jpeg, png 가능)");
				
				var viewArea = $(this).closest("td").next().children("div").attr("id");
				var preview = document.getElementById("prev_" + viewArea);
				
				alert("preview : " + preview);
				if(preview){
					alert("preview exists... remove preview");
					preview.remove();
				} else {
					alert("preview not exists...");
				}
				
				$(this).val(null);
				$(this).siblings(".fileName").text("선택된 파일 없음.");
				return false;
			}
		}
		
		/*if("franchisePlacardMap" == $(this).attr("class")){
			if(!fileCheck){
				$("#franchiseFileMap").val(null);
				$(this).siblings(".fileName").text("선택된 파일 없음.");
				return false;
			} else if (-1 == file.indexOf(".jpg") && -1 == file.indexOf(".JPG") && -1 == file.indexOf(".JPEG") && -1 == file.indexOf(".jpeg") && -1 == file.indexOf(".PNG") && -1 == file.indexOf(".png")){
				$("#franchiseFileMap").val(null);
				$(this).siblings(".fileName").text("선택된 파일 없음.");
				alert("파일 형식이 맞지 않습니다.(mp4 가능)");
				return false;
			} else {
				$("#movie_real_name").val(file);
			}
		}*/
		
		if("fileMap0" == $(this).attr("id")){
			if(-1 == file.indexOf(".avi") && -1 == file.indexOf(".mp3") && -1 == file.indexOf(".mp4") && -1 == file.indexOf(".mkv") && -1 == file.indexOf(".wmv")){
				$("#fileMap0").val(null);
				$(this).siblings(".fileName").text("선택된 파일 없음.");
				alert("파일 형식이 맞지 않습니다.(avi,mp3,mp4,mkv,wmv 가능)");
				return false;
			}else{
				$("#movie_real_name").val(file);
			}
		}

		if(file.length > 1) $(this).siblings(".fileName").text(file);
		else $(this).siblings(".fileName").text("선택된 파일 없음.");
	});	
	
});

function getExtention(fileName) {
	
	var _fileLen = filename.length;
	
	var _lastDot = filename.lastIndexOf('.');
 
	//확장자 명만 추출한 후 소문자로 변경
	var _fileExt = filename.substring(_lastDot, _fileLen).toLowerCase();
	
	return _fileExt;
}


// gnb navigator
var navi = {
	init : function(topIdx, subIdx) {
		
		// mouse over & Focus & click
		$(".lnb .topMenu").bind({
			focus : function(evt) {
				var idx = $(this).parent().index();	
				navi.onShowMenu(idx);
			},
			click : function(evt) {
				var url = $(this).attr("href");
				console.log(url);
				if(url != "#" || url.length > 2) {
					return;
				}

				evt.preventDefault();

				var idx = $(this).parent().index();
				if($(".lnb>li .subMenu").queue().length > 0
					|| $(".lnb>li:eq(" + idx + ") .subMenu").length < 1
					|| $(".lnb>li .subMenu").is(":animated")) return; 
	
				if(!$(this).parents("li").hasClass("on")) {
					navi.onShowMenu(idx);
				}
			}
		});		
		
		// page select
		if(topIdx != null || !isNaN(topIdx)) {
			navi.onSelectMenu(topIdx, subIdx);
		}
	},
		
	onShowMenu : function(idx) {
		if($(".lnb>li:eq(" + idx + ")").hasClass("on") 
			|| $(".lnb>li:eq(" + idx + ") .subMenu").length < 1
			|| $(".lnb>li .subMenu").is(":animated")) return;

				
		$(".lnb>li:eq(" + idx + ") .subMenu").stop(true, true).slideToggle(200, "easeOutBack", function(evt) {
			if($(".lnb>li:eq(" + idx + ")").hasClass("open")) $(".lnb>li:eq(" + idx + ")").removeClass("open");
			else $(".lnb>li:eq(" + idx + ")").addClass("open");

			navi.onResizeHeight();			
		});		
	},
	
	onHideMenu : function(idx) {
		$(".lnb>li:eq(" + idx + ") .subMenu").stop(true, true).slideUp(300, function(evt) {
			$(".lnb>li:eq(" + idx + ")").removeClass("open");
		});
	},
	
	// select menu
	onSelectMenu : function(topIdx, subIdx) {
		console.log(topIdx, subIdx);
		$(".lnb>li").removeClass("on");
		$(".lnb .subMenu>li").removeClass("on");

		$(".lnb>li:eq(" + topIdx + ")").addClass("on");
		$(".lnb>li:eq(" + topIdx + ") .subMenu>li:eq(" + subIdx + ")").addClass("on");
		
		navi.onResizeHeight();
	},
	
	onResizeHeight : function() {
		var menuHeight = $("#lnb").outerHeight();
		var bodyHeight = parseInt($("#body").css("min-height"));
		
		if(menuHeight > bodyHeight) {
			$("#body").css("min-height", menuHeight + "px");
		}
	}
}


// Browser Check (chrome & safari)
function checkChromeBrower() {
	var userAgent = navigator.userAgent.toLowerCase();
	$.browser.chrome = /chrome/.test(navigator.userAgent.toLowerCase());
	
	if($.browser.chrome){
		userAgent = userAgent.substring(userAgent.indexOf('chrome/') +7);
		userAgent = userAgent.substring(0,userAgent.indexOf('.'));	
		version = userAgent;
		// If it is chrome then jQuery thinks it's safari so we have to tell it it isn't
		$.browser.safari = false;
		
		return true;
	}
	
	return false;
}

// tag name check
function getTagName(e) {
	var e = e ? e : window.event;
	var event_element = e.target ? e.target : e.srcElement;
	var evt = event_element.tagName;
	
	return evt.toLowerCase();
}


// comma
function replaceComma(n) {
	var reg = /(^[+-]?\d+)(\d{3})/;
	n += '';
	while (reg.test(n))
	n = n.replace(reg, '$1' + ',' + '$2');
	
	return n;
}

// placeholder
var placeholder = {
	init : function(target) {
		$(target).each(function(i) {			
			$(this).siblings("label").addClass("placeholder");
			
			if($.trim($(this).val()) == "")
				$(this).siblings("label").show();
			else
				$(this).siblings("label").hide();
		});
		
		$(target).bind({
			focus : function(evt) {
				$(this).siblings("label").hide();
			},
			blur : function(evt) {
				if($.trim($(this).val()) == "") {
					$(this).val("");
					$(this).siblings("label").show();
				}
			}
		});
	}
}


// 팝업 열기
function onShowPopup(popupId, callback) {	
	if($(".backgroundLayer").length < 1) $("body").append('<div class="backgroundLayer"><div></div></div>');
	
	var backgroundLayer = $(".backgroundLayer");
	var popup = $("#" + popupId);

	initEvent();
	
	// background event
	$(backgroundLayer).bind("click", function(evt) {
		if($(backgroundLayer).hasClass("loadings") == true) return false;

		$(popup).hide();
		initEvent();

		if($(".layers").filter(function(i) { return $(this).css("display") === "block"; }).length < 1) {
			$(backgroundLayer).fadeOut(100);
		}
	});		
	
	// popup event
	$(popup).find(".btCloseLayer").one("click", function(evt) {
		evt.preventDefault();
		$(popup).hide();
		initEvent();

		if($(".layers").filter(function(i) { return $(this).css("display") === "block"; }).length < 1) {
			$(backgroundLayer).fadeOut(100);
		}
	});
	
	$(backgroundLayer).height($(document).height()).fadeIn(100);	
	setTimeout(function() {
		$(popup).fadeIn(300);
		popupShow();
	}, 100);

	function popupShow() {
		var windowWidth = $(window).width();
		var windowHeight = window.innerHeight;
		var documentWidth = $(document).width();
		var documentHeight = $(document).height();	
		var popupWidth = parseInt($(popup).outerWidth());
		var popupHeight = parseInt($(popup).outerHeight());
		var scrollTop = ($(popup).css("position") != "fixed") ? window.pageYOffset : 0;	
		var mt = -1 * (popupHeight / 2);	
		var left = -1 * (popupWidth / 2);
		var top = scrollTop + (windowHeight / 2) - (popupHeight / 2);
		
		$(popup).css({"top":"50%", "left":"50%", "margin-left":left, "margin-top":mt});
	
		if($.isFunction(callback)) {
			setTimeout(function() {
				callback();
			}, 100);
		}
	}
	
	function initEvent() {
		$(popup).find(".btCloseLayer").unbind("click");
		$(backgroundLayer).unbind("click");
	}
}


function checkVal(obj, type){
	var regexp;
	if("HE" == type){
		regexp = /[0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g;
	}else if("N" == type){
		regexp = /[^0-9]/gi;
	}
	
    var v = obj.value;
    if (regexp.test(v)) {
        alert("한글/영문만 입력가능 합니다.");
        obj.value = obj.value.replace(regexp, '');
    }
}

function checkEmail(obj){
	var regexp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
	var v = obj.value;
	if(!regexp.test(v)) { 
		alert("이메일 주소가 유효하지 않습니다"); 
		obj.focus(); 
		return false;
    } 
}
