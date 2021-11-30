const adminid = document.querySelector('.adminid'),
		adminName = document.querySelector('.admin_name'),
		adminGroupName = document.querySelector('.admin_group_name'),
        userid = document.querySelector('.userid'),
		dutyid = document.querySelector('.dutyid'),
		reg_date = document.querySelector('.reg_date');

const sendBtn = document.querySelector('.sendBtn');
const infocheck = document.querySelector('.infocheck')
const dateInput = document.querySelector('.date'), 
		 timeInput = document.querySelector('.time');

function realtimeApply() {
	const date = dateInput.value;
	const time = timeInput.value;
	const info = infocheck.checked;
	
	if (date === null || date === "" || time === null || time === "") {
		alert('날짜 와 시간을 선택해 주세요.');
	}else {
		const datetime = new Date(Date.UTC(date.substring(0, 4), date.substring(5,
				7)-1, date.substring(8, 10), time.substring(0, 2), time.substring(3,5)));
		
		//console.log(datetime);
		const roomName = adminid.value + Math.floor(Math.random()*1000000) +1;
		console.log(roomName);
		
		//node용 object 
		const reservation = {
			datetime : datetime,
			roomname : roomName, 
			reservname : adminid.value,
			completed : false
		}
		//spring용 object
		const realTimeVO = {
				admin_id : adminid.value,
				user_id : userid.value,
				interview_detail_date : date,
				interview_detail_time : time,
				channel_id : roomName,
				interview_duty_id : dutyid.value
		}
		//카카오 알림톡 발송 object
		const kakaoMsg = {
				msg_type: "AL",
				mt_failover: "Y",
				msg_data : {
								senderid : "025160581",
								to : "821094029331",
								//to :  userid.value,
								content : "[실시간 인터뷰 안내]\n" +reg_date.value + adminGroupName.value + "(키오스크) 로 진행된\n" + adminName.value + " 실시간 인터뷰에 신청 주셔서 감사합니다." + adminName.value + " 에서" +  date+"일"+time+"분" + "에 실시간 화상 인터뷰 진행을 요청 하였습니다.\n" + date+"일"+time+"분" + "에 맞춰 홈페이지에 접속하여 코드번호 :" + roomName + "입력하여실시간 인터뷰를 진행해 주세요."
							},
				msg_attr: {
			                    "sender_key": "370e17ef4b4cf1a15b00db956cd3a70afbc572c6",
			                    "template_code" : "R00001",
			                    "response_method": "push",
			                    "ad_flag": "Y",
			                    "attachment": {
			                    "button" : [
			                                {
			                                    "name": "홈페이지 바로가기",
			                                    "type": "WL",
			                                    "url_pc": "https://face.innospeech.com",
			                                    "url_mobile": "https://face.innospeech.com"
			                                }
			                               ]
			                    }
			    }
		}///////////////////////////////////
		
		if (info){
			fetch("insertRealtimeApply", {
				method: "POST",
				headers: {
					"Content-Type" : "application/json",
				},
				body: JSON.stringify(realTimeVO)
			}).then((res) => {
				return res.json();
			}).then((data) => {
				if (data == "success") {
					fetch("https://face.innospeech.com/reservation", {
				        method: "POST",
				        headers: {
				            "Content-Type" : "application/json",
				        },
				        body: JSON.stringify(reservation)
				    }).then((res) => {
				    	if (res.status == 200) {
				    		sendKaKaoMsg(kakaoMsg);
				    		alert('신청 완료 되었습니다.');
				    	}else {
				    		alert('신청 실패 하였습니다. 다시 시도 해주세요.');
				    	}
				    	return res.json();
				    }).then((data) => {
				        console.log(data);
				        location.href="realtimeList"
				    }).catch((error) => alert("error:", error));
				}else if (data == "already"){
					alert("이미 신청한 내역이 있습니다. 신청내역을 확인해 주세요.");
				}
			}).catch(error => alert("error:", error));
			
			dateInput.value = "";
			timeInput.value = "";
			infocheck.checked = false;
			//sendKaKaoMsg(kakaoMsg);
//			var settings = {
//					  url: "https://msggw.supersms.co:9443/v1/send/kko",
//					  method: "POST",
//					  timeout: 0,
//					  headers: {
//					    "X-IB-Client-Id": "innospeech_talk",
//					    "X-IB-Client-Passwd": "izpZSIwQH9ApNejclc5z",
//					    "Content-Type": " application/json;charset=UTF-8",
//					    "Accept": "application/json"
//					  },
//					  data :  {
//								msg_type: "AL",
//								mt_failover: "Y",
//								msg_data : {
//												senderid : "025160581",
//												to : "821094029331",
//												//to :  userid.value,
//												content : "[실시간 인터뷰 안내]\n" +reg_date.value + adminGroupName.value + "(키오스크) 로 진행된\n" + adminName.value + " 실시간 인터뷰에 신청 주셔서 감사합니다." + adminName.value + " 에서" +  date+time + "에 실시간 화상 인터뷰 진행을 요청 하였습니다.\n" + date+time + "에 맞춰 홈페이지에 접속하여 코드번호 " + roomName + "입력하여실시간 인터뷰를 진행해 주세요."
//											},
//								msg_attr: {
//							                    "sender_key": "370e17ef4b4cf1a15b00db956cd3a70afbc572c6",
//							                    "template_code" : "R00001",
//							                    "response_method": "push",
//							                    "ad_flag": "Y",
//							                    "attachment": {
//							                    "button" : [
//							                                {
//							                                    "name": "홈페이지 바로가기",
//							                                    "type": "WL",
//							                                    "url_pc": "https://face.innospeech.com",
//							                                    "url_mobile": "https://face.innospeech.com"
//							                                }
//							                               ]
//							                    }
//							    }
//						}
//					};
//
//					$.ajax(settings).done(function (response) {
//					  console.log(response);
//					});
		}else {
			alert('안내사항 확인을 체크 해주세요.')
		}
	}	    
}

const sendKaKaoMsg = (kakaoMsg) => {
	       
	 fetch("sendKakaoMsg", {
        method: "POST",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(kakaoMsg)
	}).then( (res) => {
		return res.json();
	}).then((data) => {
		console.log(data);
	})
}

sendBtn.addEventListener('click', realtimeApply);