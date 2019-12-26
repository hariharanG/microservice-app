$(document.ready(function(){
	//var encryptedPass = encryptByPublicKey($("#txtPassword").val()+$("#salt").val());
}));

/*<![CDATA[*/
var contextRoot = /*[[@{/}]]*/'';
var userType = /*[[${userType}]]*/'/CUSTOMER';
var activeLang = /*[[@{${activeLang}}]]*/'';
var baseCurrency = /*[[@{${baseCurrency}}]]*/'';
var error = /*[[@{/error}]]*/'test'
/*]]>*/

// RSA encryption 
function encryptByPublicKey(value){
	var encrypt = new JSEncrypt();
    encrypt.setPublicKey(publicKey);
    var encrypted = encrypt.encrypt(value);
    return encrypted;
	
}

var publicKey = null;

if(publicKey == null){
	$.ajax({
		url: getURL('/login/key'),
		type: 'POST',
		beforeSend: function (xhr) {
			xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf']").attr("content"));
		},
		success: function (data) {
			publicKey = data;
		}
	});
}

function getURL(path) {
	//if contextpath is found in the front.
	if (path.indexOf(contextRoot) == 0) {
		return path;
	} else {
		if (path.substring(0, 1) == '/') {
			path = path.substring(1);
		}
		return contextRoot + path;
	}
}

// AES Encryption
/*
function encryptValue(value,data){
    var aesUtil = new AesUtil(data.keySize, data.iterations);
    var ciphertext = aesUtil.encrypt(data.salt, data.iv, data.passPhrase, value);
    return ciphertext;
}
*/
