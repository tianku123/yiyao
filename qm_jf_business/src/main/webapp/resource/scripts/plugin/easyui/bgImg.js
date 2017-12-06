// JavaScript Document
$(document).ready(function() {
            
            function $id(idFlag) {
                return $('#' + idFlag);
            }
            var aBgImgConf = [
			'/resource/images/bg.jpg'
		];
            var aBgImgConfOnload = [];
            window.nBgImgCounter = 0;
            window.bBgImgChanging = false;
            window.bBgImgDraging = false;

            var oBgImg = $id('Bg_img')[0];
            var oBgImg2 = $id('Bg_img2')[0];
            var oMainBg = $id('Bg');
            window.oCurrentImg = oBgImg;

            aBgImgConfOnload.push(window.oCurrentImg);

            window.nImgOpa = 0;

            //2.核心操作，缩放图片的宽度和高度，使之与浏览器窗口匹配
            function fImgCal(obj, fCall) {
                var oImg = obj;
                var nHeight, nWidth;
                //计算图片的某个宽度，具体没细看。
                if (aBgImgConfOnload[window.nBgImgCounter]) {
                    nHeight = aBgImgConfOnload[window.nBgImgCounter].height;
                    nWidth = aBgImgConfOnload[window.nBgImgCounter].width;
                } else {
                    nHeight = oImg.height;
                    nWidth = oImg.width;
                }
                var nClientW = document.documentElement.clientWidth;
                var nClientH = document.documentElement.clientHeight;

                var nTruthHeight, nTruthWidth;
                // 如果图片是宽大于高的
                if (nHeight < nWidth) {
                    //oImg.style.width = '100%';
                    //oImg.style.height = 'auto';
                    // 那么图片真实宽度等于浏览器宽度
                    nTruthWidth = nClientW;
                    // 等比例缩放高度
                    nTruthHeight = nTruthWidth * nHeight / nWidth;
                } else {
                    //oImg.style.height = '100%';
                    //oImg.style.width = 'auto';
                    // 否则图片真实高度等于浏览器高度
                    nTruthHeight = nClientH;
                    // 等比例缩放宽度
                    nTruthWidth = nTruthHeight * nWidth / nHeight;
                }
                // 如果浏览器高度比图片的高
                if (nTruthHeight < nClientH) {
                    // 那么拉伸图片到浏览器一样高
                    oImg.style.height = '100%';
                    // 这个时候图片宽度就溢出屏幕 所以水平居中
                    oImg.style.width = 'auto';
                    oImg.style.marginLeft = -(nClientH * nWidth / nHeight - nClientW) / 2 + 'px';
                    oImg.style.marginTop = '0';
                } else {
                    // 否则拉伸图片到浏览器一样宽
                    oImg.style.width = '100%';
                    // 这个时候图片高度就溢出屏幕 所以垂直居中
                    oImg.style.height = 'auto';
                    oImg.style.marginTop = -(nClientW * nHeight / nWidth - nClientH) / 2 + 'px';
                    oImg.style.marginLeft = '0';
                }
                if (!bBgImgDraging) {
                    _fChangeMainBgAnimation(obj);
                }

                function _fChangeMainBgAnimation(obj) {
                    window.oBgImgChanging = setInterval(function() {
                        nImgOpa = nImgOpa + 0.2;
                        try {
                            obj.style.filter = 'alpha(opacity=' + nImgOpa * 100 + ')';
                            obj.style.opacity = Number(nImgOpa);
                        } catch (e) {
                            alert(e);
                            obj.style.display = 'none';
                            clearInterval(oBgImgChanging);
                            window.bBgImgChanging = false;
                        }
                        if (nImgOpa >= 1) {
                            window.bBgImgChanging = false;
                            clearInterval(window.oBgImgChanging);
                            window.bBgImgDraging = false;
                            fCall();
                        }
                    }, 40);
                }
            }

            function fWindowOnresize() {
                if (bBgImgDraging) {
                    fImgCal(window.oCurrentImg, function() { });
                    bBgImgDraging = false
                    return;
                }
            }

            function fOnresize(f) {
                window.bBgImgDraging = true;
                f();
            }
            //1. 注册window缩放事件
            window.onresize = function() {
                fOnresize(fWindowOnresize);
            };

            fOnresize(fWindowOnresize);
        });