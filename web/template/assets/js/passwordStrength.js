function setupPasswordStrength(pwdid, pbarid) {
    // reg. exp. for a weak password
    var weak = XRegExp("^(?=.*\\d{1,}).{8,}$");
    // reg. exp. for a strong password
    var strong = XRegExp("^(?=.*[a-z])(?=.*[A-Z]).+|(?=.*[!,%,&,@,#,$,^,*,?,_,~,\\-]).+$");
 
    var $this = $("#" + pwdid);
    var pbar = $("#" + pbarid).find(".ui-progressbar-value");
 
    // visualize on keyup
    $this.off('keyup.' + pwdid).on('keyup.' + pwdid, function(e) {
        visualizePasswordStrength($(this).val(), pbar, weak, strong);
    });
 
    // fix chrome issue with autofill fields
    setTimeout(function(){$this.triggerHandler('keyup.' + pwdid);}, 150);
}
 
function visualizePasswordStrength(pwd, pbar, weak, strong) {
    var pparent = pbar.parent().parent().parent();
    var weakMsg = pparent.find(".weakMsg");
    var mediumMsg = pparent.find(".mediumMsg");
    var strongMsg = pparent.find(".strongMsg");
 
    if (pwd === null || pwd.length < 1) {
        pbar.removeClass("weak medium strong");
        weakMsg.hide();
        mediumMsg.hide();
        strongMsg.hide();
        return;
    }
 
    if (!weak.test(pwd)) {
        // weak
        pbar.removeClass("medium strong").addClass("weak");
        mediumMsg.hide();
        strongMsg.hide();
        weakMsg.show();
        return;
    }
 
    if (!strong.test(pwd)) {
        // medium
        pbar.removeClass("weak strong").addClass("medium");
        weakMsg.hide();
        strongMsg.hide();
        mediumMsg.show();
        return;
    }
 
    // strong
    pbar.removeClass("weak medium").addClass("strong");
    weakMsg.hide();
    mediumMsg.hide();
    strongMsg.show();
}
