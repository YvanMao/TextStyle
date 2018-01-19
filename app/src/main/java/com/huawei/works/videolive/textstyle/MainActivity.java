package com.huawei.works.videolive.textstyle;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan.Standard;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.DrawableMarginSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TabStopSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.text.Html.fromHtml;
import static com.huawei.works.videolive.textstyle.R.id.superscriptSpan;

public class MainActivity extends AppCompatActivity implements GifDrawalbe.UpdateUIListen {
    @Bind(R.id.txtResult)
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    //字体大小样式
    @OnClick(R.id.absoluteSizeSpan)
    public void onAbsoluteSizeSpanClicked() {

        String standard = "标准字体";
        String big = "25px字体";
        String small = "10px字体";
        Spannable spn = SpannableStringBuilder.valueOf(small + standard + big);
        AbsoluteSizeSpan ass25 = new AbsoluteSizeSpan(25);
        AbsoluteSizeSpan ass10 = new AbsoluteSizeSpan(10);
        spn.setSpan(ass10, 0, small.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spn.setSpan(ass25, small.length() + standard.length(), small.length() + standard.length() + big.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //对齐样式
    @OnClick(R.id.alignmentSpan)
    public void onAlignmentSpanClicked() {
        Standard asCenter = new Standard(Alignment.ALIGN_CENTER);
        Standard asNormal = new Standard(Alignment.ALIGN_NORMAL);
        Standard asOpposite = new Standard(Alignment.ALIGN_OPPOSITE);
        String strCenter = "align center\n";
        String strNormal = "align normal\n";
        String strOpposite = "align opposite";
        int l1 = strCenter.length();
        int l2 = strNormal.length();
        int l3 = strOpposite.length();
        Spannable spn = SpannableStringBuilder.valueOf(strCenter + strNormal + strOpposite);
        spn.setSpan(asCenter, 0, l1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spn.setSpan(asNormal, l1, l1 + l2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spn.setSpan(asOpposite, l1 + l2, l1 + l2 + l3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //背景样式
    @OnClick(R.id.backgroundColorSpan)
    public void onBackgroundColorSpanClicked() {
        String strBgBlue = "蓝色背景";
        String strBgGray = "灰色背景";
        String strBgYellow = "黄色背景";
        int l1 = strBgBlue.length();
        int l2 = strBgGray.length();
        int l3 = strBgYellow.length();
        BackgroundColorSpan bcsBlue = new BackgroundColorSpan(Color.BLUE);
        BackgroundColorSpan bcsGray = new BackgroundColorSpan(Color.GRAY);
        BackgroundColorSpan bcsYellow = new BackgroundColorSpan(Color.YELLOW);
        Spannable spn = SpannableStringBuilder.valueOf(strBgBlue + strBgGray + strBgYellow);
        spn.setSpan(bcsBlue, 0, l1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spn.setSpan(bcsGray, l1, l1 + l2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spn.setSpan(bcsYellow, l1 + l2, l1 + l2 + l3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //着重样式
    @OnClick(R.id.bulletSpan)
    public void onBulletSpanClicked() {
        String p1 = "the first para.\n";
        String p2 = "the second para.\n";
        String p3 = "parcel para.";
        int l1 = p1.length(), l2 = p2.length(), l3 = p3.length();
        BulletSpan bs1 = new BulletSpan(100, Color.BLUE);
        BulletSpan bs2 = new BulletSpan();
        Parcel p = Parcel.obtain();
        p.writeInt(20);
        p.writeInt(1);
        p.writeInt(Color.YELLOW);
        p.setDataPosition(0);
        BulletSpan bs3 = new BulletSpan(p);
        Spannable spn = SpannableStringBuilder.valueOf(p1 + p2 + p3);
        spn.setSpan(bs1, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spn.setSpan(bs2, l1, l1 + l2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spn.setSpan(bs3, l1 + l2, l1 + l2 + l3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //图片+Margin样式
    @OnClick(R.id.drawableMarginSpan)
    public void onDrawableMarginSpanClicked() {
        String strStart = "no pad text\n";
        String strMiddle = "pad 30px text\n";
        String strEnd = "pad 10px text";
        int l1 = strStart.length(), l2 = strMiddle.length(), l3 = strEnd.length();
        DrawableMarginSpan dms = new DrawableMarginSpan(getDrawable(R.drawable.arrow));
        DrawableMarginSpan dmsMargin = new DrawableMarginSpan(getDrawable(R.drawable.arrow), 30);
        DrawableMarginSpan dmsBottom = new DrawableMarginSpan(getDrawable(R.drawable.arrow), 10);
        Spannable spn = SpannableStringBuilder.valueOf(strStart + strMiddle + strEnd);
        spn.setSpan(dms, 0, l1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spn.setSpan(dmsMargin, l1, l1 + l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spn.setSpan(dmsBottom, l1 + l2, l1 + l2 + l3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtResult.setText(spn);
    }

    //字体颜色
    @OnClick(R.id.foregroundColorSpan)
    public void onForegroundColorSpanClicked() {
        String strDefault = "默认颜色：红色";
        String strGreen = "绿色字体";
        String strOrange = "黄色字体";
        int l1 = strDefault.length(), l2 = strGreen.length(), l3 = strOrange.length();
        ForegroundColorSpan fcsGreen = new ForegroundColorSpan(Color.rgb(0x00, 0xff, 0x00));
        Parcel p = Parcel.obtain();
        p.writeInt(Color.YELLOW);
        p.setDataPosition(0);
        ForegroundColorSpan fcsYellow = new ForegroundColorSpan(p);
        Spannable spn = SpannableStringBuilder.valueOf(strDefault + strGreen + strOrange);
        spn.setSpan(fcsGreen, l1, l1 + l2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spn.setSpan(fcsYellow, l1 + l2, l1 + l2 + l3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtResult.setText(spn);
    }

    //图片样式
    @OnClick(R.id.imageSpan)
    public void onImageSpanClicked() {
        String text = "Android*一词的本义*指“机器人”*。Google*";
        Bitmap bm = ((BitmapDrawable) getDrawable(R.drawable.qq1)).getBitmap();
        Drawable d = getDrawable(R.drawable.qq108);
        d.setBounds(0, 0, (int) txtResult.getTextSize(), (int) txtResult.getTextSize());
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.qq139);
        ImageSpan is1 = new ImageSpan(this, bm, ImageSpan.ALIGN_BASELINE);
        ImageSpan is2 = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        ImageSpan is3 = new ImageSpan(this, R.drawable.qq139);
        ImageSpan is4 = new ImageSpan(this, uri, ImageSpan.ALIGN_BASELINE);
        Spannable spn = SpannableStringBuilder.valueOf(text);
        spn.setSpan(is1, 7, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(is2, 13, 14, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spn.setSpan(is3, 20, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(is4, 28, 29, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //文本缩进的样式
    @OnClick(R.id.leadingMarginSpan)
    public void onLeadingMarginSpanClicked() {
        String s1 = "文本段落--讲述一个被人遗忘的故事......";
        String s2 = "河南的1942--悲惨的世界，无穷无尽的饥饿和死亡。";
        String s3 = "灾民的后裔，我就是当年灾民的后裔。";
        int l1 = s1.length(), l2 = s2.length(), l3 = s3.length();
        Parcel p = Parcel.obtain();
        p.writeInt(20);
        p.writeInt(30);
        p.setDataPosition(0);
        LeadingMarginSpan.Standard lms = new LeadingMarginSpan.Standard(p);
        Spannable spn = SpannableStringBuilder.valueOf(s1 + s2 + s3);
        spn.setSpan(lms, 0, l1 + l2 + l3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //滤镜样式
    @OnClick(R.id.maskFilterSpan)
    public void onMaskFilterSpanClicked() {
        String s1 = "MaskFilterSpan";
        String s2 = "究竟应该如何使用？";
        int l1 = s1.length(), l2 = s2.length();
        MaskFilter mfBlur = new BlurMaskFilter(4, BlurMaskFilter.Blur.OUTER);
        MaskFilter mfEmboss = new EmbossMaskFilter(new float[]{10, 10, 10}, 0.1f, 5f, 5f);
        MaskFilterSpan mfsBlur = new MaskFilterSpan(mfBlur);
        MaskFilterSpan mfsEmboss = new MaskFilterSpan(mfEmboss);
        Spannable spn = SpannableStringBuilder.valueOf(s1 + s2);
        spn.setSpan(mfsBlur, 0, l1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spn.setSpan(mfsEmboss, l1, l1 + l2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //引用样式
    @OnClick(R.id.quoteSpan)
    public void onQuoteSpanClicked() {
        String part1 = "第一部分\n";
        String part2 = "第二部分床前明月光，疑是地上霜。举头望明月，低头思故乡。";
        String part3 = "第三部分\n";
        String part4 = "“组合样式实现引用”";
        int l1 = part1.length(), l2 = part2.length(), l3 = part3.length(), l4 = part4.length();
        Parcel p = Parcel.obtain();
        p.writeInt(Color.BLACK);
        p.setDataPosition(0);
        QuoteSpan qs = new QuoteSpan(p);
        Spannable spn = SpannableStringBuilder.valueOf(part1 + part2 + part3 + part4);
        spn.setSpan(qs, 0, l1 + l2 + l3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        spn.setSpan(new AbsoluteSizeSpan(100), spn.length()-l4, spn.length()-l4+1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spn.setSpan(new ForegroundColorSpan(Color.RED), spn.length()-l4, spn.length()-l4+1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spn.setSpan(new AbsoluteSizeSpan(100), spn.length()-1 , spn.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spn.setSpan(new ForegroundColorSpan(Color.RED), spn.length()-1 , spn.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        //必须重新new一个span对象，不然前面的无效

        txtResult.setText(spn);
    }

    //相对大小样式
    @OnClick(R.id.relativeSizeSpan)
    public void onRelativeSizeSpanClicked() {
        String sentence1 = "风劲角弓鸣，将军猎渭城。(0.5f)\n";
        String sentence2 = "草枯鹰眼疾，雪尽马蹄轻。(1.0f)\n";
        String sentence3 = "忽过新丰市，还归细柳营。(1.5f)\n";
        String sentence4 = "回看射雕处，千里暮云平。(默认)";
        int l1 = sentence1.length(), l2 = sentence2.length(), l3 = sentence3.length();
        RelativeSizeSpan rss1 = new RelativeSizeSpan(0.5f);
        RelativeSizeSpan rss2 = new RelativeSizeSpan(1.0f);
        Parcel p = Parcel.obtain();
        p.writeFloat(1.5f);
        p.setDataPosition(0);
        RelativeSizeSpan rss3 = new RelativeSizeSpan(p);
        Spannable spn = SpannableStringBuilder.valueOf(sentence1 + sentence2 + sentence3 + sentence4);
        spn.setSpan(rss1, 0, l1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(rss3, l1 + l2, l1 + l2 + l3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //横向缩放样式
    @OnClick(R.id.scaleXSpan)
    public void onScaleXSpanClicked() {
        String sentence1 = "月黑雁飞高，(0.5f)\n";
        String sentence2 = "单于夜遁逃。(1.0f)\n";
        String sentence3 = "欲将轻骑逐，(2.5f)\n";
        String sentence4 = "大雪满弓刀。(标准)";
        int l1 = sentence1.length(), l2 = sentence2.length(), l3 = sentence3.length();
        ScaleXSpan rss1 = new ScaleXSpan(0.5f);
        ScaleXSpan rss2 = new ScaleXSpan(1.0f);
        Parcel p = Parcel.obtain();
        p.writeFloat(2.5f);
        p.setDataPosition(0);
        ScaleXSpan rss3 = new ScaleXSpan(p);
        Spannable spn = SpannableStringBuilder.valueOf(sentence1 + sentence2 + sentence3 + sentence4);
        spn.setSpan(rss1, 0, l1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(rss2, l1, l1 + l2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(rss3, l1 + l2, l1 + l2 + l3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //删除线样式
    @OnClick(R.id.strikethroughSpan)
    public void onStrikethroughSpanClicked() {
        String sentence = "StrikethroughSpan is a line at the vertical middle position in the text.";
        int l1 = sentence.length();
        StrikethroughSpan ss = new StrikethroughSpan();
        Spannable spn = SpannableStringBuilder.valueOf(sentence);
        spn.setSpan(ss, 40, l1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //字体风格样式,粗体斜体
    @OnClick(R.id.styleSpan)
    public void onStyleSpanClicked() {
        String sentence = "StyleSpan set the style of the text.";
        int l1 = sentence.length();
        StyleSpan ssBold = new StyleSpan(Typeface.BOLD);
        StyleSpan ssItalic = new StyleSpan(Typeface.ITALIC);
        Parcel p = Parcel.obtain();
        p.writeInt(Typeface.BOLD_ITALIC);
        p.setDataPosition(0);
        StyleSpan ssBI = new StyleSpan(p);
        Spannable spn = SpannableStringBuilder.valueOf(sentence);
        spn.setSpan(ssBold, 10, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(ssItalic, 18, 26, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(ssBI, 26, l1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //下标样式，如分子式
    @OnClick(R.id.subscriptSpan)
    public void onSubscriptSpanClicked() {
        String text = "Cu2(OH)2CO3-碱式碳酸铜";
        Spannable spn = SpannableStringBuilder.valueOf(text);
        spn.setSpan(new SubscriptSpan(), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(new SubscriptSpan(), 7, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(new SubscriptSpan(), 10, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //上标样式，如平方
    @OnClick(superscriptSpan)
    public void onSuperscriptSpanClicked() {
        String text = "a2+b2=c2";
        Spannable spn = SpannableStringBuilder.valueOf(text);
        spn.setSpan(new SuperscriptSpan(), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(new SuperscriptSpan(), 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(new SuperscriptSpan(), 7, 8, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        txtResult.setText(spn);
    }

    //制表位偏移样式
    @OnClick(R.id.tabStopSpan)
    public void onTabStopSpanClicked() {
        String para1 = "\t(本段偏移50)北冥有鱼，\t其名为鲲。鲲之大，不知其几千里也。化而为鸟，其名为鹏。\r\n";
        String para2 = "\t(本段未偏移)鹏之背，不知\t其几千里也；怒而飞，其翼若垂天之云。是鸟也，海运则将徙于南冥。" + "南冥者，天池也。 ";
        TabStopSpan.Standard tss = new TabStopSpan.Standard(50);
        Spannable spn = SpannableStringBuilder.valueOf(para1 + para2);
        spn.setSpan(tss, 0, para1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //style文件定义样式
    @OnClick(R.id.textAppearanceSpan)
    public void onTextAppearanceSpanClicked() {
        String para1 = "青海长云暗雪山，\n";
        String para2 = "孤城遥望玉门关。\n";
        String para3 = "黄沙百战穿金甲，\n";
        String para4 = "不破楼兰终不还。";
        int l1 = para1.length(), l2 = para2.length(), l3 = para3.length(), l4 = para4.length();
        TextAppearanceSpan tas1 = new TextAppearanceSpan(this, android.R.style.TextAppearance_StatusBar_EventContent_Title);
        TextAppearanceSpan tas2 = new TextAppearanceSpan(this, R.style.style_red, -1);
        ColorStateList colorlist = null, colorlistLink = null;
        try {
            colorlist = ColorStateList.createFromXml(getResources(), getResources().getXml(R.xml.colorlist));
            colorlistLink = ColorStateList.createFromXml(getResources(), getResources().getXml(R.xml.colorlistlink));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextAppearanceSpan tas3 = new TextAppearanceSpan("NORMAL", Typeface.BOLD_ITALIC, 20, colorlist, colorlistLink);
        Parcel p = Parcel.obtain();
        p.writeString("SERIF");
        p.writeInt(Typeface.BOLD_ITALIC);
        p.writeInt(10);
        p.writeInt(1);
        colorlist.writeToParcel(p, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        p.writeInt(1);
        colorlist.writeToParcel(p, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        p.setDataPosition(0);
        TextAppearanceSpan tas4 = new TextAppearanceSpan(p);
        Spannable spn = SpannableStringBuilder.valueOf(para1 + para2 + para3 + para4);
        spn.setSpan(tas1, 0, l1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(tas2, l1, l1 + l2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(tas3, l1 + l2, l1 + l2 + l3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(tas4, l1 + l2 + l3, l1 + l2 + l3 + l4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //字体样式，只能设置系统自带的字体
    @OnClick(R.id.typefaceSpan)
    public void onTypefaceSpanClicked() {
        String text1 = "The font is a strange thing.\n";
        String text2 = "The font is a strange thing.\n";
        String text3 = "The font is a strange thing.\n";
        String text4 = "The font is a strange thing.";
        int l1 = text1.length(), l2 = text2.length(), l3 = text3.length(), l4 = text4.length();
        TypefaceSpan tsSANS_SERIF = new TypefaceSpan("sans_serif");
        TypefaceSpan tsMONOSPACE = new TypefaceSpan("monospace");
        Parcel p = Parcel.obtain();
        p.writeString("serif");
        p.setDataPosition(0);
        TypefaceSpan tsSERIF = new TypefaceSpan(p);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font.ttf");
        MyTypefaceSpan typeFont = new MyTypefaceSpan(typeface);

        Spannable spn = SpannableStringBuilder.valueOf(text1 + text2 + text3 + text4);
        spn.setSpan(tsSANS_SERIF, 0, l1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(tsMONOSPACE, l1, l1 + l2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(tsSERIF, l1 + l2, l1 + l2 + l3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(typeFont, l1 + l2 + l3, l1 + l2 + l3 + l4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //下划线样式
    @OnClick(R.id.underlineSpan)
    public void onUnderlineSpanClicked() {
        String sentence1 = "安能摧眉折腰事权贵，";
        String sentence2 = "使我不得开心颜。";
        int l1 = sentence1.length(), l2 = sentence2.length();
        UnderlineSpan us = new UnderlineSpan();
        Spannable spn = SpannableStringBuilder.valueOf(sentence1 + sentence2);
        spn.setSpan(us, l1, l1 + l2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtResult.setText(spn);
    }

    //超链接样式
    @OnClick(R.id.urlSpan)
    public void onUrlSpanClicked() {
        String text = "点击这里看到哪儿去...再点击这里看看呢？";
        URLSpan us = new URLSpan("http://www.baidu.com");
        Parcel p = Parcel.obtain();
        p.writeString("http://www.sina.com.cn");
        p.setDataPosition(0);
        URLSpan us2 = new URLSpan(p);
        Spannable spn = SpannableStringBuilder.valueOf(text);
        spn.setSpan(us, 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(us2, 15, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //txtResult.setMovementMethod(LinkMovementMethod.getInstance());
        txtResult.setText(spn);
    }

    private Vector<Drawable> drawables = new Vector();

    //Html样式
    @OnClick(R.id.htmlSpan)
    public void onCustomSpanClicked() {
        final HashMap<String, Integer> imgMap = new HashMap();
        imgMap.put("qq1.png", R.drawable.qq1);
        imgMap.put("qq1gif.gif", R.drawable.qq1gif);

        String textStr = "本月已成功邀请 <strong><font color='#FF0000'>" + 100 + "</font></strong>人";
        String imgStr = "</br><IMG src='qq1.png'></br></br><IMG src='qq1gif.gif'>";
        Spanned spn = Html.fromHtml(textStr + imgStr, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                if (source.endsWith(".gif")) {
                    GifDrawalbe drawable = new GifDrawalbe(getApplicationContext(), imgMap.get(source));
                    //drawable.addListen(this);
                    drawables.add(drawable);
                    return drawable;
                } else {
                    Drawable drawable = getResources().getDrawable(imgMap.get(source).intValue());
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    return drawable;
                }
            }
        }, null);
        txtResult.setText(spn);
        Iterator var4 = this.drawables.iterator();

        while (var4.hasNext()) {
            Drawable gifDrawable = (Drawable) var4.next();
            ((GifDrawalbe) gifDrawable).addListen(this);
            ((GifDrawalbe) gifDrawable).readFrames(true);
        }
    }

    //Gif图片样式
    @OnClick(R.id.gifSpan)
    public void onGifSpanClicked() {
        String textStr = "本月已成功邀请 <strong><font color='#FF0000'>" + 100 + "</font></strong>人";

        txtResult.setText(fromHtml(textStr));

    }

    @Override
    public void updateUI() {
        txtResult.invalidate();
    }
}
