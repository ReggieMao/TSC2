package com.ebei.library;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;

import com.ebei.tgnet.TLRPC;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.MessagesController;
import com.ebei.tsc.UserConfig;
import com.ebei.ui.ActionBar.Theme;
import com.ebei.ui.Components.AvatarDrawable;
import com.ebei.ui.Components.BackupImageView;
import com.ebei.ui.PhotoViewer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by MaoLJ on 2018/7/18.
 * 工具类
 */

public class Util {

    public static final String TAG = "Util";

    /**
     * 判断一个字符串是否为空
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }

    /**
     * dp转像素
     */
    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 检测本地网络是否打开
     */
    public static boolean isNetworkOpen(Context ctx) {
        ConnectivityManager connManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo().isAvailable();
    }

    /**
     * 得到屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 软键盘挡住按钮处理
     */
    public static void addLayoutListener(View main, View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //1、获取main在窗体的可视区域
                main.getWindowVisibleDisplayFrame(rect);
                //2、获取main在窗体的不可视区域高度，在键盘没有弹起时，main.getRootView().getHeight()调节度应该和rect.bottom高度一样
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                int screenHeight = main.getRootView().getHeight();//屏幕高度
                //3、不可见区域大于屏幕本身高度的1/4：说明键盘弹起了
                if (mainInvisibleHeight > screenHeight / 4) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    // 4､获取Scroll的窗体坐标，算出main需要滚动的高度
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                    //5､让界面整体上移键盘的高度
                    main.scrollTo(0, srollHeight);
                } else {
                    //3、不可见区域小于屏幕高度1/4时,说明键盘隐藏了，把界面下移，移回到原有高度
                    main.scrollTo(0, 0);
                }
            }
        });
    }

    /**
     * 沉浸式状态栏
     */
    public static void immersiveStatus(Activity activity, boolean needBlack) {
        //判断版本是否支持沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (needBlack) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /**
     * 从appConfig.properties配置文件获取信息
     * type: 1表示测试版本，2表示正式版本
     */
    public static String getPropertiesURL(String s, int type) {
        String url = null;
        Properties pro = new Properties();
        try {
            if (type == 1) {
                pro.load(Util.class.getResourceAsStream("/assets/debugUrl.properties"));
            } else if (type == 2) {
                pro.load(Util.class.getResourceAsStream("/assets/releaseUrl.properties"));
            }
            url = pro.getProperty(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Activity mContext) {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取当前时间
     */
    public static String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前时间1
     */
    public static String getNowTime1() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 限制最多输入小数点后6位
     */
    public static void setPoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 6) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 6 + 1);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 格式化double类型，小数点后保留size位(去尾法)
     */
//    public static String point(double v, int size) {
//        BigDecimal bg = new BigDecimal(v + "");
//        String s = bg + "000000";
//        String need = "";
//        if (size == 6)
//            need = s.substring(0, s.indexOf(".") + 7);
//        else if (size == 2)
//            need = s.substring(0, s.indexOf(".") + 3);
//        return need;
//    }

    /**
     * MD5加密
     */
    public static String encrypt(String plaintext) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = plaintext.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 某个时间距离现在的时间差
     */
    public static String timeDifference(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date userDate;
        long userTime = 0;
        try {
            userDate = simpleDateFormat.parse(time);
            userTime = userDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date nowDate = new Date(System.currentTimeMillis());
        long nowTime = nowDate.getTime();
        long timeDiff = nowTime - userTime;
        if (timeDiff < 60 * 60 * 1000)
            return (timeDiff / (1000 * 60) + 1) + "分钟以前";
        else if (timeDiff >= 60 * 60 * 1000 && timeDiff < 24 * 60 * 60 * 1000)
            return (timeDiff / (1000 * 60 * 60) + 1) + "小时以前";
        else if (timeDiff >= 24 * 60 * 60 * 1000 && timeDiff < 240 * 60 * 60 * 1000)
            return (timeDiff / (1000 * 60 * 60 * 24) + 1) + "天以前";
        else
            return time.substring(5, 10);
    }

    /**
     * 判断是否是红包消息
     */
    public static boolean isRedPac(String messageText) { // {{{{{TSCRedBag$2827$恭喜}}}}}你收到一个红包，请下载TSC领取http://www.tschain.top/mobile_download.html
        String[] arrar = messageText.split("[$]");
        return arrar.length == 3 && (arrar[0] + "$").equals(Constants.RED_PACKET_CODE1) && arrar[2].contains("}}}}}");
    }

    /**
     * 判断是否是红包领取消息
     */
    public static boolean isRedReceive(String messageText) { // {{{{{TSCRedBagSign$Nick Yi}}}}}
        String[] arrar = messageText.split("[$]");
        return arrar.length == 2 && (arrar[0] + "$").equals(Constants.RED_RECEIVE_CODE1) && arrar[1].endsWith(Constants.RED_RECEIVE_CODE2);
    }

    /**
    * 判断是否是转账消息
     */
    public static boolean isTransfer(String messageText) { // {{{{{TSCTransfer$1000$TSC}}}}}你收到一笔转账，请下载TSC查看http://www.tschain.top/mobile_download.html
        String[] arrar = messageText.split("[$]");
        return arrar.length == 3 && (arrar[0] + "$").equals(Constants.TRANSFER_CODE1) && arrar[2].contains("}}}}}");
    }

    /**
     * 红包寄语限制性设置
     */
    public static void setRedContent(EditText editText) {
        final int nameMaxLen = 30;
        // 昵称输入过滤器（限制中文最多输入15位，英文和其他字母最多输入30位）
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                int dindex = 0;
                int count = 0;

                while (count <= nameMaxLen && dindex < dest.length()) {
                    char c = dest.charAt(dindex++);
                    if (c < 128) {
                        count = count + 1;
                    } else {
                        count = count + 2;
                    }
                }

                if (count > nameMaxLen) {
                    return dest.subSequence(0, dindex - 1);
                }

                int sindex = 0;
                while (count <= nameMaxLen && sindex < source.length()) {
                    char c = source.charAt(sindex++);
                    if (c < 128) {
                        count = count + 1;
                    } else {
                        count = count + 2;
                    }
                }

                if (count > nameMaxLen) {
                    sindex --;
                }

                return source.subSequence(0, sindex);
            }
        };
        editText.setFilters(new InputFilter[] {filter});
    }

    /**
     * 设置Tele信息
     */
    public static void setTeleInfo(String uid, BackupImageView avatar) {
        TLRPC.User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(Integer.parseInt(uid));
        TLRPC.FileLocation photo = null;
        TLRPC.FileLocation photoBig = null;
        if (user != null && user.photo != null) {
            photo = user.photo.photo_small;
            photoBig = user.photo.photo_big;
        }
        AvatarDrawable avatarDrawable = new AvatarDrawable(user, true);
        avatarDrawable.setColor(Theme.getColor(Theme.key_avatar_backgroundInProfileBlue));
        avatar.setRoundRadius(AndroidUtilities.dp(200));
        if (avatar != null) {
            avatar.setImage(photo, "50_50", avatarDrawable);
            avatar.getImageReceiver().setVisible(!PhotoViewer.isShowingImage(photoBig), false);
        }
    }

    public static String eliminateZero(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("##########################.############################");
        return decimalFormat.format(value);
    }

    /**
     * 解决科学技术法问题
     */
    public static String getScientificCountingMethodAfterData(Double aDouble, Integer scale) {
        BigDecimal bigDecimal = new BigDecimal(aDouble.toString());
        bigDecimal = bigDecimal.setScale(scale, BigDecimal.ROUND_DOWN);
        return bigDecimal.stripTrailingZeros().toPlainString();
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(long lt){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 添加联系人
     */
    public static void addContact(Activity activity, String name, String phoneNumber) {
        // 创建一个空的ContentValues
        ContentValues values = new ContentValues();

        Uri rawContactUri = activity.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();

        values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 内容类型
        values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // 联系人名字
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
        // 向联系人URI添加联系人名字
        activity.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        values.clear();

        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话号码
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);
        // 电话类型
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        // 向联系人电话号码URI添加电话号码
        activity.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        values.clear();

        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        activity.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
    }

    /**
     * 根据手机号查询通讯录名字
     */
    public static String queryNameByNum(String num, Context context) {
        Cursor cursorOriginal = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                        ContactsContract.CommonDataKinds.Phone.NUMBER + "='" + num + "'", null, null);
        if (null != cursorOriginal) {
            if (cursorOriginal.getCount() > 1) {
                return null;
            } else {
                if (cursorOriginal.moveToFirst()) {
                    return cursorOriginal.getString(cursorOriginal.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

}

