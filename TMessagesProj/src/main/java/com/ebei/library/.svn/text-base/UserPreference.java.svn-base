package com.ebei.library;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MaoLJ on 2018/7/18.
 * 本地保存
 */

public class UserPreference {

    public static final String SESSION_ID = "session_id";
    public static final String SECRET = "secret";
    public static final String FOTA_PASSWORD = "fota_password";
    public static final String ACCOUNT = "account";
    public static final String ADDRESS = "address";
    public static final String NOW_DATE = "now_date";
    public static final String SEND_MESSAGE_COUNT = "send_message_count";
    public static final String HAS_UPDATE_TIP = "has_update_tip";
    public static final String VERSION_CODE = "version_code";
    public static final String VERSION_LOG = "version_log";
    public static final String VERSION_URL = "version_url";
    public static final String WHERE_TO_STICKERS = "where_to_stickers";
    public static final String IS_GROUP = "is_group";
    public static final String CURRENT_USER_ID = "current_user_id";
    public static final String HAD_IN_TRANSFER = "had_in_transfer";
    public static final String HAD_IN_RED = "had_in_red";
    public static final String HAD_SEND_TRANSFER = "had_send_transfer";
    public static final String HAD_SEND_RED = "had_send_red";
    public static final String HAD_RECEIVE_RED = "had_receive_red";
    public static final String HAD_SHOW_RED_DIALOG = "had_show_red_dialog";
    public static final String CURRENT_CLICK_RED = "current_click_red";
    public static final String CURRENT_CHAT_ID = "current_chat_id";

    public static final String CURRENT_CONTACTS_TITLE = "current_contacts_title";
    public static final String CURRENT_DIALOGS_TITLE = "current_dialogs_title";

    public static final String CONTACTS_TAB_SHOW = "contacts_tab_show";
    public static final String DIALOGS_TAB_SHOW = "dialogs_tab_show";
    public static final String CHANNEL_TAB_SHOW = "channel_tab_show";
    public static final String GROUP_TAB_SHOW = "group_tab_show";

    public static final String IS_MX_USER = "is_mx_user";

    public static String sp_name;

    private static SharedPreferences getSharePreferences() {
        return BaseApp.getAppContext().getSharedPreferences(sp_name, Context.MODE_PRIVATE);
    }

    public static void putInt(String key, int value) {
        getSharePreferences().edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int value){
        return getSharePreferences().getInt(key,value);
    }

    public static void putString(String key, String value) {
        getSharePreferences().edit().putString(key, value).apply();
    }

    public static String getString(String key, String def) {
        return getSharePreferences().getString(key, def);
    }

}
