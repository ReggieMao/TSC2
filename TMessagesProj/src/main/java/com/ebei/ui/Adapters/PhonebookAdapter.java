/*
 * This is the source code of Telegram for Android v. 1.3.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2017.
 */

package com.ebei.ui.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.ebei.PhoneFormat.PhoneFormat;
import com.ebei.library.UserPreference;
import com.ebei.tgnet.TLRPC;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.ContactsController;
import com.ebei.tsc.LocaleController;
import com.ebei.tsc.UserConfig;
import com.ebei.tsc.support.widget.RecyclerView;
import com.ebei.ui.Cells.DividerCell;
import com.ebei.ui.Cells.LetterSectionCell;
import com.ebei.ui.Cells.UserCell;
import com.ebei.ui.Components.RecyclerListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhonebookAdapter extends RecyclerListView.SectionsAdapter {

    private int currentAccount = UserConfig.selectedAccount;
    private Context mContext;
    private String mMxPhone;

    public PhonebookAdapter(Context context, String mxPhone) {
        mContext = context;
        mMxPhone = mxPhone;
    }

    private HashMap<String, ArrayList<Object>> getUsersSectionsDict(int userType) {
        HashMap<String, ArrayList<Object>> need = new HashMap<>();

        HashMap<String, ArrayList<Object>> usersSectionsDict = ContactsController.getInstance(currentAccount).phoneBookSectionsDict;
        ArrayList<String> sortedUsersSectionsArray = ContactsController.getInstance(currentAccount).phoneBookSectionsArray;

        if (userType == 0) {
            need = usersSectionsDict;
        } else if (userType == 1) {
            for (int i = 0; i < sortedUsersSectionsArray.size(); i ++) {
                ArrayList<Object> subNeed = new ArrayList<>();
                if (usersSectionsDict.get(sortedUsersSectionsArray.get(i)) != null && usersSectionsDict.get(sortedUsersSectionsArray.get(i)).size() > 0) {
                    for (int d = 0; d < usersSectionsDict.get(sortedUsersSectionsArray.get(i)).size(); d ++) {
                        if (usersSectionsDict.get(sortedUsersSectionsArray.get(i)).get(d) instanceof ContactsController.Contact) {
                            if (((ContactsController.Contact) usersSectionsDict.get(sortedUsersSectionsArray.get(i)).get(d)).getPhones().size() == 1 &&
                                    mMxPhone.contains(((ContactsController.Contact) usersSectionsDict.get(sortedUsersSectionsArray.get(i)).get(d)).getPhones().get(0) + ",")) {
                                subNeed.add(usersSectionsDict.get(sortedUsersSectionsArray.get(i)).get(d));
                            }
                        }
                    }
                }
                if (subNeed.size() > 0) {
                    need.put(sortedUsersSectionsArray.get(i), subNeed);
                }
            }
        }

        return need;
    }

    private ArrayList<String> getSortedUsersSectionsArray() {
        ArrayList<String> need = new ArrayList<>();
        HashMap<String, ArrayList<Object>> usersSectionsDict = getUsersSectionsDict(UserPreference.getInt(UserPreference.IS_MX_USER, 0));

        for (Map.Entry<String, ArrayList<Object>> stringArrayListEntry : usersSectionsDict.entrySet()) {
            Map.Entry element = (Map.Entry) stringArrayListEntry;
            Object strKey = element.getKey();
            need.add(strKey.toString());
        }

        return need;
    }

    public Object getItem(int section, int position) {
//        HashMap<String, ArrayList<Object>> usersSectionsDict = ContactsController.getInstance(currentAccount).phoneBookSectionsDict;
//        ArrayList<String> sortedUsersSectionsArray = ContactsController.getInstance(currentAccount).phoneBookSectionsArray;

        HashMap<String, ArrayList<Object>> usersSectionsDict = getUsersSectionsDict(UserPreference.getInt(UserPreference.IS_MX_USER, 0));
        ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

        if (section < sortedUsersSectionsArray.size()) {
            ArrayList<Object> arr = usersSectionsDict.get(sortedUsersSectionsArray.get(section));
            if (position < arr.size()) {
                return arr.get(position);
            }
        }
        return null;
    }

    @Override
    public boolean isEnabled(int section, int row) {
//        HashMap<String, ArrayList<Object>> usersSectionsDict = ContactsController.getInstance(currentAccount).phoneBookSectionsDict;
//        ArrayList<String> sortedUsersSectionsArray = ContactsController.getInstance(currentAccount).phoneBookSectionsArray;

        HashMap<String, ArrayList<Object>> usersSectionsDict = getUsersSectionsDict(UserPreference.getInt(UserPreference.IS_MX_USER, 0));
        ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

        return row < usersSectionsDict.get(sortedUsersSectionsArray.get(section)).size();
    }

    @Override
    public int getSectionCount() {
//        ArrayList<String> sortedUsersSectionsArray = ContactsController.getInstance(currentAccount).phoneBookSectionsArray;

        ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

        return sortedUsersSectionsArray.size();
    }

    @Override
    public int getCountForSection(int section) {
//        HashMap<String, ArrayList<Object>> usersSectionsDict = ContactsController.getInstance(currentAccount).phoneBookSectionsDict;
//        ArrayList<String> sortedUsersSectionsArray = ContactsController.getInstance(currentAccount).phoneBookSectionsArray;

        HashMap<String, ArrayList<Object>> usersSectionsDict = getUsersSectionsDict(UserPreference.getInt(UserPreference.IS_MX_USER, 0));
        ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

        if (section < sortedUsersSectionsArray.size()) {
            ArrayList<Object> arr = usersSectionsDict.get(sortedUsersSectionsArray.get(section));
            int count = arr.size();
            if (section != sortedUsersSectionsArray.size() - 1) {
                count++;
            }
            return count;
        }
        return 0;
    }

    @Override
    public View getSectionHeaderView(int section, View view) {
//        ArrayList<String> sortedUsersSectionsArray = ContactsController.getInstance(currentAccount).phoneBookSectionsArray;

        ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

        if (view == null) {
            view = new LetterSectionCell(mContext);
        }
        LetterSectionCell cell = (LetterSectionCell) view;
        if (section < sortedUsersSectionsArray.size()) {
            cell.setLetter(sortedUsersSectionsArray.get(section));
        } else {
            cell.setLetter("");
        }
        return view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = new UserCell(mContext, 58, 1, false);
                ((UserCell) view).setNameTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                break;
            case 1:
            default:
                view = new DividerCell(mContext);
                view.setPadding(AndroidUtilities.dp(LocaleController.isRTL ? 28 : 72), 0, AndroidUtilities.dp(LocaleController.isRTL ? 72 : 28), 0);
                break;
        }
        return new RecyclerListView.Holder(view);
    }

    @Override
    public void onBindViewHolder(int section, int position, RecyclerView.ViewHolder holder) {
        switch (holder.getItemViewType()) {
            case 0:
                UserCell userCell = (UserCell) holder.itemView;
                Object object = getItem(section, position);
                TLRPC.User user = null;
                if (object instanceof ContactsController.Contact) {
                    ContactsController.Contact contact = (ContactsController.Contact) object;
                    if (contact.user != null) {
                        user = contact.user;
                    } else {
                        userCell.setCurrentId(contact.contact_id);
                        userCell.setData(null, ContactsController.formatName(contact.first_name, contact.last_name), contact.phones.isEmpty() ? "" : PhoneFormat.getInstance().format(contact.phones.get(0)), 0);
                    }
                } else {
                    user = (TLRPC.User) object;
                }
                if (user != null) {
                    userCell.setData(user, null, PhoneFormat.getInstance().format("+" + user.phone), 0);
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int section, int position) {
//        HashMap<String, ArrayList<Object>> usersSectionsDict = ContactsController.getInstance(currentAccount).phoneBookSectionsDict;
//        ArrayList<String> sortedUsersSectionsArray = ContactsController.getInstance(currentAccount).phoneBookSectionsArray;

        HashMap<String, ArrayList<Object>> usersSectionsDict = getUsersSectionsDict(UserPreference.getInt(UserPreference.IS_MX_USER, 0));
        ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

        return position < usersSectionsDict.get(sortedUsersSectionsArray.get(section)).size() ? 0 : 1;
    }

    @Override
    public String getLetter(int position) {
//        ArrayList<String> sortedUsersSectionsArray = ContactsController.getInstance(currentAccount).phoneBookSectionsArray;

        ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

        int section = getSectionForPosition(position);
        if (section == -1) {
            section = sortedUsersSectionsArray.size() - 1;
        }
        if (section >= 0 && section < sortedUsersSectionsArray.size()) {
            return sortedUsersSectionsArray.get(section);
        }
        return null;
    }

    @Override
    public int getPositionForScrollProgress(float progress) {
        return (int) (getItemCount() * progress);
    }
}
