/*
 * This is the source code of Telegram for Android v. 1.3.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2017.
 */

package com.ebei.ui.Adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.ebei.PhoneFormat.PhoneFormat;
import com.ebei.library.UserPreference;
import com.ebei.tgnet.TLRPC;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.ContactsController;
import com.ebei.tsc.FileLog;
import com.ebei.tsc.LocaleController;
import com.ebei.tsc.MessagesController;
import com.ebei.tsc.UserConfig;
import com.ebei.tsc.Utilities;
import com.ebei.tsc.support.widget.RecyclerView;
import com.ebei.ui.Cells.UserCell;
import com.ebei.ui.Components.RecyclerListView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PhonebookSearchAdapter extends RecyclerListView.SelectionAdapter {

    private Context mContext;
    private ArrayList<Object> searchResult = new ArrayList<>();
    private ArrayList<CharSequence> searchResultNames = new ArrayList<>();
    private Timer searchTimer;
    private String mMxPhone;

    public PhonebookSearchAdapter(Context context, String mxPhone) {
        mContext = context;
        mMxPhone = mxPhone;
    }

    public void search(final String query) {
        try {
            if (searchTimer != null) {
                searchTimer.cancel();
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (query == null) {
            searchResult.clear();
            searchResultNames.clear();
            notifyDataSetChanged();
        } else {
            searchTimer = new Timer();
            searchTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        searchTimer.cancel();
                        searchTimer = null;
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                    processSearch(query);
                }
            }, 200, 300);
        }
    }

    private ArrayList<ContactsController.Contact> getContactsCopy(int currentAccount, int userType) {
        ArrayList<ContactsController.Contact> need = new ArrayList<>();
        ArrayList<ContactsController.Contact> contactsCopy = new ArrayList<>(ContactsController.getInstance(currentAccount).contactsBook.values());

        if (userType == 0) {
            need = contactsCopy;
        } else if (userType == 1) {
            for (int i = 0; i < contactsCopy.size(); i ++) {
                if (contactsCopy.get(i).getPhones() != null && contactsCopy.get(i).getPhones().size() == 1) {
                    if (mMxPhone.contains(contactsCopy.get(i).getPhones().get(0) + ",")) {
                        need.add(contactsCopy.get(i));
                    }
                }
            }
        }

        return need;
    }

    private ArrayList<TLRPC.TL_contact> getContactsCopy2(int currentAccount, int userType) {
        ArrayList<TLRPC.TL_contact> need = new ArrayList<>();
        ArrayList<TLRPC.TL_contact> contactsCopy2 = new ArrayList<>(ContactsController.getInstance(currentAccount).contacts);

        if (userType == 0) {
            need = contactsCopy2;
        } else if (userType == 1) {
            for (int i = 0; i < contactsCopy2.size(); i ++) {
                String phone = MessagesController.getInstance(currentAccount).getUser(contactsCopy2.get(i).user_id).phone;
                if (mMxPhone.contains(phone.substring(2, phone.length()) + ",")) {
                    need.add(contactsCopy2.get(i));
                }
            }
        }

        return need;
    }

    private void processSearch(final String query) {
        AndroidUtilities.runOnUIThread(() -> {
            final int currentAccount = UserConfig.selectedAccount;
//            final ArrayList<ContactsController.Contact> contactsCopy = new ArrayList<>(ContactsController.getInstance(currentAccount).contactsBook.values());
            final ArrayList<ContactsController.Contact> contactsCopy = getContactsCopy(currentAccount, UserPreference.getInt(UserPreference.IS_MX_USER, 0));

//            final ArrayList<TLRPC.TL_contact> contactsCopy2 = new ArrayList<>(ContactsController.getInstance(currentAccount).contacts);
            final ArrayList<TLRPC.TL_contact> contactsCopy2 = getContactsCopy2(currentAccount, UserPreference.getInt(UserPreference.IS_MX_USER, 0));

            Utilities.searchQueue.postRunnable(() -> {
                String search1 = query.trim().toLowerCase();
                if (search1.length() == 0) {
                    updateSearchResults(query, new ArrayList<>(), new ArrayList<>());
                    return;
                }
                String search2 = LocaleController.getInstance().getTranslitString(search1);
                if (search1.equals(search2) || search2.length() == 0) {
                    search2 = null;
                }
                String search[] = new String[1 + (search2 != null ? 1 : 0)];
                search[0] = search1;
                if (search2 != null) {
                    search[1] = search2;
                }

                ArrayList<Object> resultArray = new ArrayList<>();
                ArrayList<CharSequence> resultArrayNames = new ArrayList<>();
                SparseBooleanArray foundUids = new SparseBooleanArray();

                for (int a = 0; a < contactsCopy.size(); a++) {
                    ContactsController.Contact contact = contactsCopy.get(a);
                    String name = ContactsController.formatName(contact.first_name, contact.last_name).toLowerCase();
                    String tName = LocaleController.getInstance().getTranslitString(name);
                    String name2;
                    String tName2;
                    if (contact.user != null) {
                        name2 = ContactsController.formatName(contact.user.first_name, contact.user.last_name).toLowerCase();
                        tName2 = LocaleController.getInstance().getTranslitString(name);
                    } else {
                        name2 = null;
                        tName2 = null;
                    }
                    if (name.equals(tName)) {
                        tName = null;
                    }

                    int found = 0;
                    for (String q : search) {
                        if (name2 != null && (name2.startsWith(q) || name2.contains(" " + q)) || tName2 != null && (tName2.startsWith(q) || tName2.contains(" " + q))) {
                            found = 1;
                        } else if (contact.user != null && contact.user.username != null && contact.user.username.startsWith(q)) {
                            found = 2;
                        } else if (name.startsWith(q) || name.contains(" " + q) || tName != null && (tName.startsWith(q) || tName.contains(" " + q))) {
                            found = 3;
                        }
                        if (found != 0) {
                            if (found == 3) {
                                resultArrayNames.add(AndroidUtilities.generateSearchName(contact.first_name, contact.last_name, q));
                            } else if (found == 1) {
                                resultArrayNames.add(AndroidUtilities.generateSearchName(contact.user.first_name, contact.user.last_name, q));
                            } else {
                                resultArrayNames.add(AndroidUtilities.generateSearchName("@" + contact.user.username, null, "@" + q));
                            }
                            if (contact.user != null) {
                                foundUids.put(contact.user.id, true);
                            }
                            resultArray.add(contact);
                            break;
                        }
                    }
                }

                for (int a = 0; a < contactsCopy2.size(); a++) {
                    TLRPC.TL_contact contact = contactsCopy2.get(a);
                    if (foundUids.indexOfKey(contact.user_id) >= 0) {
                        continue;
                    }
                    TLRPC.User user = MessagesController.getInstance(currentAccount).getUser(contact.user_id);
                    String name = ContactsController.formatName(user.first_name, user.last_name).toLowerCase();
                    String tName = LocaleController.getInstance().getTranslitString(name);
                    if (name.equals(tName)) {
                        tName = null;
                    }

                    int found = 0;
                    for (String q : search) {
                        if (name.startsWith(q) || name.contains(" " + q) || tName != null && (tName.startsWith(q) || tName.contains(" " + q))) {
                            found = 1;
                        } else if (user.username != null && user.username.startsWith(q)) {
                            found = 2;
                        }

                        if (found != 0) {
                            if (found == 1) {
                                resultArrayNames.add(AndroidUtilities.generateSearchName(user.first_name, user.last_name, q));
                            } else {
                                resultArrayNames.add(AndroidUtilities.generateSearchName("@" + user.username, null, "@" + q));
                            }
                            resultArray.add(user);
                            break;
                        }
                    }
                }

                updateSearchResults(query, resultArray, resultArrayNames);
            });
        });
    }

    protected void onUpdateSearchResults(String query) {

    }

    private void updateSearchResults(final String query, final ArrayList<Object> users, final ArrayList<CharSequence> names) {
        AndroidUtilities.runOnUIThread(() -> {
            onUpdateSearchResults(query);
            searchResult = users;
            searchResultNames = names;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return searchResult.size();
    }

    public Object getItem(int i) {
        return searchResult.get(i);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
            default:
                view = new UserCell(mContext, 8, 0, false);
                ((UserCell) view).setNameTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                break;
        }
        return new RecyclerListView.Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            UserCell userCell = (UserCell) holder.itemView;

            Object object = getItem(position);
            TLRPC.User user = null;
            if (object instanceof ContactsController.Contact) {
                ContactsController.Contact contact = (ContactsController.Contact) object;
                if (contact.user != null) {
                    user = contact.user;
                } else {
                    userCell.setCurrentId(contact.contact_id);
                    userCell.setData(null, searchResultNames.get(position), contact.phones.isEmpty() ? "" : PhoneFormat.getInstance().format(contact.phones.get(0)), 0);
                }
            } else {
                user = (TLRPC.User) object;
            }
            if (user != null) {
                userCell.setData(user, searchResultNames.get(position), PhoneFormat.getInstance().format("+" + user.phone), 0);
            }
        }
    }

    @Override
    public boolean isEnabled(RecyclerView.ViewHolder holder) {
        return true;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}
