package com.miaomi.fenbei.voice.ui.mine.about;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.voice.R;

@Route(path = "/app/agreement")
public class AgreementActivity extends BaseActivity {
    private TextView tv_content;
    private TextView titleTv;

    public final static String AGREE_TYPE = "AGREE_TYPE";
    public final static int AGREE_TYPE_REG = 0;
    public final static int AGREE_TYPE_PAY = 1;
    public final static int AGREE_TYPE_MAKING_FRIENDS = 2;
    public final static int AGREE_TYPE_CONCEAL = 3;
    public final static int AGREE_TYPE_CONCEAL_ZC = 4;
    public final static int AGREE_TYPE_ADOLESCENT = 5;
    public final static int AGREE_TYPE_GXJJ = 6;
    public final static int AGREE_TYPE_DSRZ = 7;
    public final static int AGREE_TYPE_ZYZYXY = 8;
    private int type;


    public static void startActivity(Context context,int type) {
        Intent intent = new Intent(context, AgreementActivity.class);
        intent.putExtra(AGREE_TYPE,type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_about_agreement;
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra(AGREE_TYPE,0);
        tv_content = findViewById(R.id.tv_content);
        titleTv = findViewById(R.id.main_tv);
//        if (type == AGREE_TYPE_REG){
//            titleTv.setText("用户协议");
//        }
//        if (type == AGREE_TYPE_PAY){
//            titleTv.setText("用户充值协议");
//            tv_content.setText(R.string.pay_protocal);
//        }
//        if (type == AGREE_TYPE_MAKING_FRIENDS) {
//            titleTv.setText("广播交友规则");
//            tv_content.setText(R.string.protocol.making_friends_protocol);
//        }

//        if (type == AGREE_TYPE_CONCEAL) {
//            titleTv.setText("隐私政策");
//            tv_content.setText(R.string.login_conceal);
//        }

//        if (type == AGREE_TYPE_CONCEAL_ZC) {
//            titleTv.setText("隐私保护政策");
//            tv_content.setText(R.string.login_conceal_zc);
//        }
        if (type == AGREE_TYPE_ADOLESCENT) {
            titleTv.setText("未成年保护计划");
            tv_content.setText(R.string.minor_protection_scheme);
        }
//        if (type == AGREE_TYPE_GXJJ){
//            titleTv.setText("共享经济合作伙伴协议");
//            tv_content.setText(R.string.minor_gxjj);
//        }
//        if (type == AGREE_TYPE_DSRZ){
//            titleTv.setText("大神认证协议");
//            tv_content.setText(Html.fromHtml("<p class=\"MsoNormal\">\n" +
//                    "声明：<span>APP</span>没有委托任何第三方进行<span>“</span>大神<span>”</span>业务线下推广及代理。对于未经授权的代理机构、个人所销售商品或承诺服务，<span>APP</span>将不承担任何法律责任。<span><br />\n" +
//                    "&nbsp;<br />\n" +
//                    "</span>本协议由掌翼（深圳）网络科技有限公司（下称<span>“APP”</span>或<span>“</span>甲方<span>”</span>）及同意并承诺遵守本协议规定使用<span>“</span>大神<span>”</span>服务的法律实体（下称<span>“</span>大神<span>”</span>或<span>“</span>乙方<span>”</span>）共同缔结，本协议具有合同效力。<span><br />\n" +
//                    "&nbsp;<br />\n" +
//                    "</span>一、<span>&nbsp;</span>合作内容<span>&nbsp;<br />\n" +
//                    "1.1</span>乙方可利用甲方的<span>APP</span>大神服务平台（包括<span>APP</span>客户端）发布乙方作为大神的技能资质信息（以下简称<span>“</span>大神信息<span>”</span>），包括但不限于个人基本信息等乙方大神条件信息。<span><br />\n" +
//                    "1.2</span>乙方利用甲方在平台上发布的个人技能资质信息，为乙方提供网络宣传推广服务<span>(</span>大神信息的互联网发布、互动活动推广及与此有关的互联网技术服务<span>)</span>，进而为甲方的用户提供服务信息。<span><br />\n" +
//                    "1.3</span>合作时间：本协议经大神在线接受且经过<span>APP</span>审核通过后即告生效，除非本协议规定的终止条件发生，本协议将持续有效。双方另有约定的除外。<span><br />\n" +
//                    "1.4</span>广告服务：乙方在线提交资质审核申请成为大神，<span>APP</span>有权根据《大神资质审核规范》驳回开通请求。乙方通过大神平台通进行个人信息推广，经甲方审核通过的乙方技能资质信息需按照自助设定的投放维度，采用网上银行支付相应费用。<span><br />\n" +
//                    "1.5</span>收益提现：乙方根据订单服务所获收益可申请大神收入提现。<span><br />\n" +
//                    "1.6</span>定价权：乙方应遵守<span>“APP”</span>内的定价规则，不得擅自对<span>“APP”</span>内的定价规则进行任何形式的更改，包括但不限于：线上游戏、线上娱乐、文艺生活、心理咨询、外语、线下游戏等模块。乙方不得以任何形式绕开<span>“APP”</span>内定价规则进行线上或线下交易，亦不得通过私下交易的方式逃避<span>“APP”</span>内定价规则。<span><br />\n" +
//                    "&nbsp;<br />\n" +
//                    "</span>二、<span>&nbsp;</span>甲方权利与义务<span><br />\n" +
//                    "2.1</span>&emsp;甲方保证经甲方审核通过的乙方信息在<span>APP</span>平台上能正确显示。乙方提供的大神信息能够顺利、及时上线。<span><br />\n" +
//                    "2.2</span>&emsp;甲方可以为乙方提供监测数据，包括大神个人技能资质信息、点赞信息、陪玩信息、评论信息。<span><br />\n" +
//                    "2.3</span>&emsp;当第三方通知甲方，认为乙方在平台上发布的信息存在重大问题时，甲方将以普通非专业人员的知识水平标准对相关内容或行为进行初步判别，如判别认定该内容或行为不当或具有违法性质，甲方有权删除相关信息或<span>/</span>和停止对乙方提供服务。<span><br />\n" +
//                    "</span>（<span>1</span>）只有透露第三方用户的个人资料，才能提供用户所要求的产品和服务；<span><br />\n" +
//                    "</span>（<span>2</span>）根据有关的法律法规要求；<span><br />\n" +
//                    "</span>（<span>3</span>）按照相关政府主管部门的要求；<span><br />\n" +
//                    "</span>（<span>4</span>）为维护<span>APP</span>的合法权益。<span><br />\n" +
//                    "2.4</span>&emsp;在乙方注册<span>APP</span>账户，使用<span>APP</span>提供的产品或服务，<span>APP</span>会收集乙方的个人身份识别资料，并会将这些资料用于：改进为乙方提供的服务及信息内容。<span><br />\n" +
//                    "&nbsp;<br />\n" +
//                    "</span>三、乙方权利与义务<span><br />\n" +
//                    "3.1</span>&emsp;资质文件提交：乙方欲使用本协议下服务，必须向甲方提交如下文件（以下简称<span>“</span>资质文件<span>”</span>）：乙方出具个人技能资质（需通过图片上传，图片内具有资质所需信息）；<span><br />\n" +
//                    "3.2</span>&emsp;平台服务：乙方完整、准确提交上述资质文件并经甲方审核通过后，甲方即为乙方开通平台服务。开通后，乙方可在平台上发布大神信息，经甲方审核通过后即可显示在平台上。乙方应妥善保管其账号及密码信息，不得以任何形式擅自转让或授权他人使用，否则须自行承担由此引起的法律责任。<span><br />\n" +
//                    "3.3</span>&emsp;平台合法使用：<span><br />\n" +
//                    "</span>（<span>1</span>）遵守国家法律法规：乙方保证在使用甲方服务时实施的所有行为均遵守国家法律、法规以及社会公共利益。如有违反导致任何法律后果的发生，乙方将以自己的名义独立承担相应的法律责任。如因此给甲方或关联方、合作方造成损失或因此导致甲方或关联方、合作方承担连带责任的，甲方或关联方、合作方有权向乙方追偿。<span><br />\n" +
//                    "</span>（<span>2</span>）不侵害第三方合法权益：乙方承诺拥有合法的权利和资格向平台上传有关信息并进行推广，且前述行为未对任何第三方合法权益，包括但不限于第三方知识产权、物权等构成侵害，如乙方因违反上述约定而引起的任何第三方提起的投诉、索赔，诉讼或行政责任的均与甲方无关，因此造成甲方损失的则由乙方全部赔偿。<span><br />\n" +
//                    "3.4</span>&emsp;信息变更的通知：乙方在本协议期限内如变更或新增任何信息（包括但不限于资质文件及其所含信息、经营信息、推广信息），应保证在一个工作日内通知甲方并更新至平台上。<span><br />\n" +
//                    "</span>（<span>1</span>）资质信息变更：协议期内，资质文件及其所含信息发生任何变更，乙方应重新编辑技能资质信息并重新申请成为大神，如资质文件及其所含信息的变更使得乙方不再具备成为大神履行本协议的情形出现时，甲方有权立即终止本协议，不承担任何赔偿及法律责任。乙方同意为其未在约定时间内通知或更新其信息承担全部法律责任。<span><br />\n" +
//                    "</span>（<span>2</span>）经营信息变更：如游戏、等级、角色等游戏资质信息需变更，乙方可在线进行修改，在经甲方审核通过后，甲方承诺在<span>3</span>个工作日内完成平台上显示信息的变更。<span><br />\n" +
//                    "3.5</span>&emsp;信息真实、准确、合法、有效性承诺：乙方保证其向甲方提供的全部信息，包括但不限于证明文件、经营信息、推广信息等，真实、准确、合法，且在协议履行期间都处于合法有效期内，不可存在任何误导、遗漏、虚假或隐瞒，否则，因此给第三方和<span>/</span>或甲方带来损失的，应由乙方独立承担全部法律责任并予以赔偿。<span><br />\n" +
//                    "3.6</span>&emsp;企业名称及商标合法使用授权：乙方授权甲方合法使用本合作涉及的中文及外文的商标、<span>logo</span>、企业名称等，并承诺其合法享有对前述相关商标、<span>logo</span>、企业名称等完整、无瑕疵的知识产权，并有权授予甲方为实现本协议目的使用。<span><br />\n" +
//                    "&nbsp;<br />\n" +
//                    "</span>四、解约<span><br />\n" +
//                    "4.1</span>&emsp;乙方同意赔偿由于使用甲方服务（包括但不限于将乙方资料展示在互联网上）或违反本协议而给甲方或关联方、合作方造成的任何损失（包括由此产生的全额的诉讼费用和律师费）。乙方同意甲方不对任何由乙方张贴的资料承担任何责任；由于此类资料对其它用户造成的损失由乙方自行全部承担。<span><br />\n" +
//                    "4.2</span>&emsp;除本协议另有约定之外，如一方发生违约行为，守约方可以书面通知方式（纸质文件或短信、电子邮件、平台通知信息等）要求违约方在指定的时限内停止违约行为，并就违约行为造成的损失进行索赔，如违约方未能按时停止违约行为，则守约方除就其损失获得所有赔偿外亦有权立即终止本协议。<span><br />\n" +
//                    "4.3</span>&emsp;不可抗力免责条款：如由于不可抗力事件，包括但不限于互联网连接故障、电脑、通讯或其他系统的故障、电力故障、罢工、劳动争议、暴乱、骚乱、火灾、洪水、风暴、爆炸、战争、政府行为、法院的命令或第三方的不作为或任何其他类似事件，致使合同的部分或全部不能履行或延迟履行，则甲方不承担任何违约责任，但双方应将不可抗力所造成的影响控制在最小范围内。<span><br />\n" +
//                    "&nbsp;<br />\n" +
//                    "</span>五、法律适用及解决争议的方式<span><br />\n" +
//                    "5.1</span>&emsp;法律适用：本协议的订立、效力、履行和解释及争议的解决适用中华人民共和国法律、法规和计算机、互联网行业的规范。合同条款如与相关法律、法规内容抵触，该条款无效，但不影响其他条款的法律效力。<span><br />\n" +
//                    "5.2</span>&emsp;解决争议的方式：因本协议订立、解释和履行发生的争议，双方应友好协商解决，如协商不成，提交甲方所在地人民法院解决。<span><br />\n" +
//                    "&nbsp;<br />\n" +
//                    "</span>六、合同终止<span><br />\n" +
//                    "6.1</span>&emsp;提前通知解除：协议任何一方均可以提前十五天以书面通知（纸质文件或短信、电子邮件、平台通知信息等）的方式提前终止本协议。<span><br />\n" +
//                    "6.2</span>&emsp;协议终止后有关事项的处理：<span><br />\n" +
//                    "</span>（<span>1</span>）协议终止后，甲方有权删除乙方平台服务账户和<span>/</span>或与之相关的任何信息，有权终止转发任何未曾阅读或发送的信息给乙方或第三方，甲方亦不就终止协议而对乙方或任何第三方承担任何责任；<span><br />\n" +
//                    "</span>（<span>2</span>）协议终止后，甲方有权保留乙方的注册数据及以前的相关行为记录。如乙方在协议终止前在平台上存在违法行为或违反协议的行为，甲方仍可行使本协议所规定的权利；<span><br />\n" +
//                    "</span>（<span>3</span>）协议终止之前，乙方已经创建的推广信息尚未完成实现的，<span>APP</span>有权在协议终止时同时删除与此相关的信息，同时该部分推广费用不予退还。<span><br />\n" +
//                    "&nbsp;<br />\n" +
//                    "</span>七、保密条款<span><br />\n" +
//                    "7.1</span>&emsp;本协议所称商业秘密包括但不限于本协议、任何补充协议所述内容及在合作过程中涉及的其他秘密信息。任何一方未经商业秘密提供方书面同意，均不得将该信息向第三方（有关法律、法规、政府部门、证券交易所或其他监管机构要求和双方的法律、会计、商业及其他顾问、雇员除外）披露、传播、编辑或展示。<span><br />\n" +
//                    "7.2</span>&emsp;任何一方违反本保密条款，需赔偿守约方全部直接和间接损失。<span><br />\n" +
//                    "7.3</span>&emsp;该商业秘密已为公众所知悉的，披露方不承担责任。<span><br />\n" +
//                    "7.4</span>&emsp;本协议终止后，双方仍承担保密条款下的相关义务，保密期将另持续三年。<span><br />\n" +
//                    "&nbsp;<br />\n" +
//                    "</span>八、特殊免责条款<span><br />\n" +
//                    "8.1</span>&emsp;乙方理解网站或客户端为正常运行需进行停机维护，如因此造成乙方广告不能按计划发布，乙方承诺不追究甲方法律责任，但甲方有义务尽力避免服务中断或将中断限制在最短时间内。<span><br />\n" +
//                    "8.2</span>&emsp;广告投放过程中，基于对可释放量等因素的考虑，实际每日投放并不总是按总投放量平均进行，或实际每日投放与预订投放不完全一致，乙方对此给予理解认可，但甲方尽可能将上述影响减少到最低。<span><br />\n" +
//                    "8.3</span>&emsp;由于黑客攻击、第三方公司软件屏蔽或非因甲方技术原因导致乙方广告无法播放的，乙方对此予以理解，并承诺不追究甲方责任，但甲方尽可能将上述影响减少到最低。<span><br />\n" +
//                    "8.4</span>&emsp;乙方同意，甲方因上述情形不能按计划发布广告的，不视为甲方违约。但甲方尽可能按原计划规定的时间和位置发布广告，或与乙方协商确定其他解决方案。<span><br />\n" +
//                    "&nbsp;<br />\n" +
//                    "</span>九、附则<span><br />\n" +
//                    "9.1</span>&emsp;除非获得另一方的书面许可，任何一方不得将其在本协议项下的权利或义务转让或以其他方式让与给他人。<span><br />\n" +
//                    "9.2</span>&emsp;本协议附件是本协议不可分割的一部分，与本协议正文具有同等法律效力。<span><br />\n" +
//                    "&nbsp;<br />\n" +
//                    "</span>十、特别声明<span><br />\n" +
//                    "</span>鉴于：我国《合同法》第<span>39</span>条规定：采用格式条款订立合同的，提供格式条款的一方应当遵循公平原则确定当事人之间的权利和义务，并采取合理的方式提请对方注意免除或者限制其责任的条款，按照对方的要求，对该条款予以说明。<span><br />\n" +
//                    "</span>掌翼（深圳）网络科技有限公司在此依法做出特别声明如下：<span><br />\n" +
//                    "</span>掌翼（深圳）网络科技有限公司采取合理的方式提请用户注意的义务将通过如下方式实现：在本协议中掌翼（深圳）网络科技有限公司以明确的足以引起用户注意的加重字体、斜体、下划线、颜色标记等合理方式提醒用户注意相关条款<span>(</span>需要强调的是，还包括用户应特别注意任何未明确标记的含有<span>“</span>不承担<span>”</span>、<span>“</span>免责<span>”“</span>不得<span>”</span>等形式用语的条款<span>)</span>，该等条款的确认将导致用户在特定情况下的被动、不便、损失，请用户在确认同意本协议之前再次阅读上述条款。双方确认上述条款非属于《合同法》第<span>40</span>条规定的<span>“</span>免除其责任、加重对方责任、排除对方主要权利的<span>”</span>的条款，掌翼（深圳）网络科技有限公司尊重用户的权利尤其是诉讼的权利，但作为全球运营的公司，掌翼（深圳）网络科技有限公司在尊重用户诉讼权利的同时建议诉讼管辖地法院为掌翼（深圳）网络科技有限公司所在地法院，而用户选择同意合同并使用服务即视为双方对此约定达成了一致意见。<span><br />\n" +
//                    "</span>用户如有任何需要说明条款的要求，请立即停止使用服务，双方在此确认掌翼（深圳）网络科技有限公司已依法履行了根据用户要求对相关条款进行说明的法定义务，掌翼（深圳）网络科技有限公司已给予用户充足的时间与充分的选择权来决定是否缔结本协议。<span><br />\n" +
//                    "</span>鉴于掌翼（深圳）网络科技有限公司已依法明确了上述条款、履行了格式条款制订方的义务，用户点击同意或下一步，将被视为且应当被视为用户已经完全注意并同意了本协议所有条款尤其是提醒用户注意的条款的合法性及有效性，用户不应当以掌翼（深圳）网络科技有限公司未对格式条款以合理方式提醒用户注意，或未根据用户要求尽到说明义务为理由而声称或要求法院或其它任何第三方确认相关条款非法或无效。<span><br />\n" +
//                    "&nbsp;<br />\n" +
//                    "&nbsp;</span><span></span>\n" +
//                    "\t</p>\n" +
//                    "</p>\n" +
//                    "<p>\n" +
//                    "\t<br />\n" +
//                    "</p>"));
//        }
//        if (type == AGREE_TYPE_ZYZYXY){
//            titleTv.setText("自由职业者服务合作协议");
//            tv_content.setText(Html.fromHtml("<strong>本《自由职业者服务合作协议》(下称“本协议”)由乙方(您)与甲方(我们)共同签署，本协议具有合同效力。</strong>\n" +
//                    "            <i class=\"important\"><span>【审慎阅读】</span>为维护您的权益，在您与我们签署本协议之前，请您务必审慎阅读、充分理解各条款内容，特别是权利义务条款、免责条款和争议管辖条款。前述条款将以粗体标识，您应重点阅读。</i>\n" +
//                    "            <i class=\"important\"><span>【签约动作】</span>当您按照提示填写您的个人信息，通过签字、页面勾选、点击确认或其他方式选择接受本协议，即表示您理解和同意本协议的全部内容并与我们达成协议，此后您不得以未阅读/不理解本协议内容或类似言辞做任何形式的抗辩。 如果您不同意本协议的任意内容，或者无法正确理解我们对条款的解释，请您立即停止签署/注册/申请程序。本协议条款若在我们授权的第三方网络平台上发布，自您线上点击确认之日起生效;若为书面签署，自您签署本协议之日起生效。</i>\n" +
//                    "            <i class=\"important\"><span>【合作关系】</span>您理解并确认，您作为具有时间和技能盈余的自由职业者，与我们以及我们的合作单位深圳美明赞文化传播有限公司(下称“合作单位”)通过本协议将建立合作关系，适用《合同法》《民法总则》和其他民事法律，不适用 《劳动合同法》。</i>\n" +
//                    "            <h2>协议条款</h2>\n" +
//                    "            <label>一、总则</label>\n" +
//                    "            <p>\n" +
//                    "                1.1 因我们与合作单位项目合作需要，我们特选用您按照我们与合作单位相关合同约定提供平台直播服务。<br>\n" +
//                    "                1.2 您是具有完全民事行为能力的自然人，了解我们和合作单位的服务需求，足以行使本协议项下权利和履行本协议项下之义务，且无任何违法犯罪记录。<br>\n" +
//                    "                1.3 本协议内容包括协议正文以及所有我们和合作单位已经发布的或将来可能发布的与本协议相关的协议、声明、业务规则及公告指引内容(下统称“规则”)，所有规则为本协议不可分割的一部分，与协议正文具有同等法律效力。<br>\n" +
//                    "                1.4 本协议合作期限为 1 年，自签署之日起计算。\n" +
//                    "            </p>\n" +
//                    "            <label>二、您的权利和义务</label>\n" +
//                    "            <p>\n" +
//                    "                2.1 您承诺和保证，您具有提供本协议服务所对应的相应资质和专业能力，并自行提供、配置与项目服务有关的环境和设备，您依据本协议约定提供相关服务收取相应费用不违反任何法律法规和政策规定。若依据相关规定，您提供相关服务和收取相应费用，应通过所在机构或以其他法律规定的方式进行，则您自行负责办理全部事宜，若因您的自身原因未办理前述事宜，则您应承担由此引起的全部法律后果，并赔偿由此给我们及合作单位造成的全部经济损失。<br>\n" +
//                    "                2.2 您应按照本协议及/或我们与合作单位相关合同规定的标准完成工作。<br>\n" +
//                    "                2.3 您应按时尽责地完成我们和合作单位要求的工作内容，并达到规定的质量标准，不得有侵犯他人知识产权、财产权、人身权、肖像权、隐私权、商业秘密或其他合法权益以及其他违反国家法律法规、国家政策、或有悖于公序良俗的内容。<br>\n" +
//                    "                2.4 未经我们和合作单位同意，您不得擅自将服务转包、转让给他人，否则我们有权立即终止本协议，并要求您支付与转包/转让项目交易金额相当的违约金，并赔偿由此给我们及合作单位造成的全部经济损失。<br>\n" +
//                    "                2.5 您在服务期间须维护我们(包括第三方技术提供方等)和合作单位的品牌、形象和声誉，并对自己的言论行为负责。<br>\n" +
//                    "                2.6 您在提供服务过程中与任一方或第三人所产生的争议，我们不承担任何法律责任。<br>\n" +
//                    "                2.7 除本协议约定的服务外，您不能以我们及/或合作单位名义开展任何与完成约定的工作任务无关的业务或活动。<br>\n" +
//                    "                2.8 您在合作期间不得与合作单位建立劳动/劳务合同关系或其他类似劳动人事法律关系，并不得担任法定代表人、董事、监事或股东。您/本人确认与我们不存在法律上和事实上的人事劳动关系，不属于我们人事管理权限范围内的个人，也并非法定代表人、董事、监事或股东。<br>\n" +
//                    "                2.9 您保证不会基于从我们或合作单位获得的信息而私下与合作单位达成交易，否则我们有权立即终止本协议，并要求您支付相应违约金。<br>\n" +
//                    "                2.10 您按照我们及/或合作方要求完成的服务成果及其所形成的所有知识产权，除非您与我们及/或合作方有另行书面约定，否则其所有权归合作单位所有，合作单位在行使该等知识产权时，无须取得您和/或任何第三方的同意。\n" +
//                    "            </p>\n" +
//                    "            <label>三、我们的权利和义务</label>\n" +
//                    "            <p>\n" +
//                    "                3.1 您应根据我们和合作单位要求提供服务，服务期间您应遵守我们和合作单位有关服务内容、服务质量等方面的要求。我们和合作单位可根据自身的运营要求制定具体工作安排，您应予遵守。<br>\n" +
//                    "                3.2 我们和合作单位有权对您为我们提供服务的工作成果进行验收。<br>\n" +
//                    "                3.3 我们负责对您的费用进行结算并按时足额支付。<br>\n" +
//                    "                3.4 您同意，您不可撤销地授权我们和合作单位有权为提供前述费用结算服务的需要获取您的相关信息(包括但不限于个人身份信息、实名登记手机号码、银行账户相关信息等)，并将您的相关信息提交第三方机构进行身份验证。若您提供任何错误、虚假、失效或不完整的资料，或者我们或合作单位有合理的理由怀疑资料为错误、虚假、过期或不完整，我们或合作单位有权暂停或终止向您提供部分或全部服务，对此我们或合作单位不承担任何责任，您承诺并同意负担因此所产生的所有损失，包括但不限于直接损失、间接损失。若因国家法律法规、部门规章或监管机构的要求，我们或合作单位需要您补充提供任何相关资料时，如您不能及时配合提供，我们或合作单位有权暂停或终止向您提供部分或全部服务。\n" +
//                    "            </p>\n" +
//                    "            <label>四、费用结算</label>\n" +
//                    "            <p>\n" +
//                    "                4.1 我们按照本协议和我们与合作单位相关合同(包括业务规则和服务标准)规定的结算标准以及您的实际服务情况向您结算费用，结算时间根据项目完成进度确定。您应缴纳的个人所得税及其他相关税费由我们负责代征缴。<br>\n" +
//                    "                4.2 您应向我们提供以您本人实名注册/认证的对应账户信息，以及相关必要证件。若具体账户信息在第三方网络平台在线提供的，以您在线提供的信息为准。您应保证您提供的与结算相关的信息真实、合法、准确、有效，为您本人所有，不存在洗钱等违法行为，不影响本协议效力。如有违反，您独自承担相应责任。<br>\n" +
//                    "                4.3 我们以您提供的收款帐户为依据支付您的费用，因您提供的收款帐户不实造成的一切损失由您自行承担。如您帐号变更或发生不可用等情况时，应及时书面通知我们或在我们指定的第三方网络平台进行变更操作，否则，由此造成的一切损失由您自行负责。<br>\n" +
//                    "                4.4 我们收到合作单位合作款项后 1-2 个工作日内按时足额向您支付费用，您应遵守我们的支付政策。<br>\n" +
//                    "                4.5 基于您与我们的合作关系，我们除依据本协议向您支付费用外，我们不承担您的任何社会保险、公积金、残保金等福利待遇。<br>\n" +
//                    "                4.6 您/本人委托我们代理本合同交易项下的涉税事务，包括代理报税、代开发票和代开个人完税证明，为此您/本人确认，我们根据本协议授权提交的相关申报资料和信息是真实、完整、准确并符合相关法律法规政策规定。\n" +
//                    "            </p>\n" +
//                    "            <label>五、保密责任</label>\n" +
//                    "            <p>\n" +
//                    "                5.1 您应在完成签署/注册/申请程序后任何时间，保守因履行本协议需要接触或掌握的我们、合作单位或第三方网络平台的商业秘密。商业秘密指我们或合作单位提供的或对第三方承担保密义务的，或者您在双方合作期间了解到的，以及与业务相关能为我们或合作单位带来经济利益、具有实用性的、非公开的所有信息，包括但不限于:经营信息、本协议条款内容、您应获得的费用、结算方式、项目参与人员名单等不为公众所知悉的信息。除我们书面许可外，您不得在任何时间 以任何方式和目的披露、使用或允许他人使用以前项手段获取的我们和合作单位的商业秘密。<br>\n" +
//                    "                5.2 我们对您与结算相关的非公开信息进行严格保密，不非法披露或使用您的信息。<br>\n" +
//                    "                5.3 本协议保密责任条款在本协议终止后仍然有效。\n" +
//                    "            </p>\n" +
//                    "            <label>六、免责条款您理解并同意，在下列情形下，我们和合作单位无须承担任何责任</label>\n" +
//                    "            <p>\n" +
//                    "                6.1 任何由于黑客攻击、计算机病毒侵入或发作、电信部门技术调整导致之影响、因政府管制而造成的暂时性关闭、由于第三方原因(包括不可抗力)及其他非我们和合作单位的过错而造成的费用结算失败等;<br>\n" +
//                    "                6.2 您向我们和合作单位提供的与结算相关信息由于错误、不完整、不真实等原因造成结算失败或遭受其他任何损失，与我们和合作单位无关。<br>\n" +
//                    "                6.3 因法律法规政策变动或监管机构要求导致双方无法继续合作。\n" +
//                    "            </p>\n" +
//                    "            <label>七、协议变更、终止与违约责任</label>\n" +
//                    "            <p>\n" +
//                    "                7.1 本协议在发生下列情形时终止:<br>\n" +
//                    "                7.1.1 您根据本协议相关规则退出合作停止提供服务的;<br>\n" +
//                    "                7.1.2 我们和合作单位变更业务合作模式而不需要您继续履行本协议的;<br>\n" +
//                    "                7.1.3 我们和合作单位终止合作的;<br>\n" +
//                    "                7.1.4 我们和合作单位根据本协议相关规则有理由认为您不适合继续提供服务的;<br>\n" +
//                    "                7.1.5 因法律法规政策变动或监管机构要求导致双方无法继续合作的;<br>\n" +
//                    "                7.1.6 您违反本合同项下任何一项义务或保证;<br>\n" +
//                    "                7.1.7 我们可随时经提前七日通知您而终止本协议且不视为违约;<br>\n" +
//                    "                7.1.8 其他原因导致本协议不可能或不适合再继续履行的。<br>\n" +
//                    "                7.2 本协议的提前终止不影响双方已经产生的权利义务关系。<br>\n" +
//                    "                7.3 双方应按本协议约定履行，如有违反，守约方有权要求对方及时改正;造成对方损失的，守约方有权要求违约方赔偿。<br>\n" +
//                    "                7.4 因不可抗力造成损失的，彼此不负赔偿责任，但发生不可抗力一方应及时将有关情况通知另一方，并尽最大努力进行补救。本协议所称不可抗力是指不能预见、不能克服并不能避免且对一方当事人造成重大影响的客观事件，包括但不限于自然灾害如洪水、地震、火灾和风暴等以及社会事件如战争、动乱、政府行为、黑客事件、大范围电信事故等。\n" +
//                    "            </p>\n" +
//                    "            <label>八、其他</label>\n" +
//                    "            <p>\n" +
//                    "                8.1 因本协议的订立和履行，我们或合作单位需要向您发送的任何通知、指示、催告、文件扫描件、电子文档或其他任何形式的意思表示，均可通过包括但不限于书面、电子邮件、短信、站内信、系统通知或页面公告等形式中的一种或几种发送或接收。<br>\n" +
//                    "                8.2 请您理解，为向您提供更优质的服务，您同意我们在经营策略调整时，可以将本协议项下的部分或全部服务转交我们的关联主体运营或履行。在发生合并、分立、收购、资产转让时，我们也可向第三方转让本服务下的相关资产。<br>\n" +
//                    "                8.3 请您理解，在不影响您提供服务和费用结算的情况下，合作单位基于经营策略调整可将其与我们之间相关协议项下的部分或全部服务转交其关联主体运营或履行。<br>\n" +
//                    "                8.4 请您理解，我们和合作单位有权根据经营业务需要修改本协议条款(包括所有规则)。<br>\n" +
//                    "                8.5 本协议的订立、效力、解释、履行和争议解决均适用中华人民共和国法律。<br>\n" +
//                    "                8.6 因履行本协议发生争议，双方应友好协商解决;协商不成应提请甲方住所地人民法院诉讼解决。\n" +
//                    "            </p>"));
//        }
    }
}
