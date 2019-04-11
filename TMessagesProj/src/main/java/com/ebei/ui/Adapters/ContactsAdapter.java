/*
 * This is the source code of Telegram for Android v. 1.3.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2017.
 */

package com.ebei.ui.Adapters;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.ebei.library.UserPreference;
import com.ebei.tgnet.TLRPC;
import com.ebei.tsc.AndroidUtilities;
import com.ebei.tsc.ContactsController;
import com.ebei.tsc.LocaleController;
import com.ebei.tsc.MessagesController;
import com.ebei.tsc.R;
import com.ebei.tsc.UserConfig;
import com.ebei.tsc.support.widget.RecyclerView;
import com.ebei.ui.Cells.DividerCell;
import com.ebei.ui.Cells.GraySectionCell;
import com.ebei.ui.Cells.LetterSectionCell;
import com.ebei.ui.Cells.TextCell;
import com.ebei.ui.Cells.UserCell;
import com.ebei.ui.Components.RecyclerListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactsAdapter extends RecyclerListView.SectionsAdapter {

    private int currentAccount = UserConfig.selectedAccount;
    private Context mContext;
    private int onlyUsers;
    private boolean needPhonebook;
    private SparseArray<TLRPC.User> ignoreUsers;
    private SparseArray<?> checkedMap;
    private boolean scrolling;
    private boolean isAdmin;
    private String mMxPhone;

    public ContactsAdapter(Context context, int onlyUsersType, boolean arg2, SparseArray<TLRPC.User> arg3, boolean arg4, String mxPhone) {
        mContext = context;
        onlyUsers = onlyUsersType;
        needPhonebook = arg2;
        ignoreUsers = arg3;
        isAdmin = arg4;
        mMxPhone = mxPhone;
    }

    public void setCheckedMap(SparseArray<?> map) {
        checkedMap = map;
    }

    public void setIsScrolling(boolean value) {
        scrolling = value;
    }

    private HashMap<String, ArrayList<TLRPC.TL_contact>> getUsersSectionsDict(int userType) {
        HashMap<String, ArrayList<TLRPC.TL_contact>> need = new HashMap<>();

        HashMap<String, ArrayList<TLRPC.TL_contact>> usersSectionsDict = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).usersMutualSectionsDict : ContactsController.getInstance(currentAccount).usersSectionsDict;
        ArrayList<String> sortedUsersSectionsArray = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(currentAccount).sortedUsersSectionsArray;

        if (userType == 0) {
            need = usersSectionsDict;
        } else if (userType == 1) {
            for (int i = 0; i < sortedUsersSectionsArray.size(); i ++) {
                ArrayList<TLRPC.TL_contact> subNeed = new ArrayList<>();
                if (usersSectionsDict.get(sortedUsersSectionsArray.get(i)) != null && usersSectionsDict.get(sortedUsersSectionsArray.get(i)).size() > 0) {
                    for (int d = 0; d < usersSectionsDict.get(sortedUsersSectionsArray.get(i)).size(); d ++) {
                        String phone = MessagesController.getInstance(currentAccount).getUser(usersSectionsDict.get(sortedUsersSectionsArray.get(i)).get(d).user_id).phone;
                        if (mMxPhone.contains(phone.substring(2, phone.length()) + ",")) {
                            subNeed.add(usersSectionsDict.get(sortedUsersSectionsArray.get(i)).get(d));
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
        HashMap<String, ArrayList<TLRPC.TL_contact>> usersSectionsDict = getUsersSectionsDict(UserPreference.getInt(UserPreference.IS_MX_USER, 0));

        for (Map.Entry<String, ArrayList<TLRPC.TL_contact>> stringArrayListEntry : usersSectionsDict.entrySet()) {
            Map.Entry element = (Map.Entry) stringArrayListEntry;
            Object strKey = element.getKey();
            need.add(strKey.toString());
        }

        return need;
    }

    private ArrayList<ContactsController.Contact> getPhoneBookContacts(int userType) {
        ArrayList<ContactsController.Contact> need = new ArrayList<>();
        ArrayList<ContactsController.Contact> phoneBookContacts = ContactsController.getInstance(currentAccount).phoneBookContacts;

        if (userType == 0) {
            need = phoneBookContacts;
        } else if (userType == 1) {
            for (int i = 0; i < phoneBookContacts.size(); i ++) {
                if (phoneBookContacts.get(i).getUser() == null && phoneBookContacts.get(i).getPhones().size() == 1) {
                    if (mMxPhone.contains(phoneBookContacts.get(i).getPhones().get(0) + ",")) {
                        need.add(phoneBookContacts.get(i));
                    }
                }
            }
        }

        return need;
    }

    public Object getItem(int section, int position) {
//        HashMap<String, ArrayList<TLRPC.TL_contact>> usersSectionsDict = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).usersMutualSectionsDict : ContactsController.getInstance(currentAccount).usersSectionsDict;
//        ArrayList<String> sortedUsersSectionsArray = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(currentAccount).sortedUsersSectionsArray;

        HashMap<String, ArrayList<TLRPC.TL_contact>> usersSectionsDict = getUsersSectionsDict(UserPreference.getInt(UserPreference.IS_MX_USER, 0));
        ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

        if (onlyUsers != 0 && !isAdmin) {
            if (section < sortedUsersSectionsArray.size()) {
                ArrayList<TLRPC.TL_contact> arr = usersSectionsDict.get(sortedUsersSectionsArray.get(section));
                if (position < arr.size()) {
                    return MessagesController.getInstance(currentAccount).getUser(arr.get(position).user_id);
                }
            }
            return null;
        } else {
            if (section == 0) {
                return null;
            } else {
                if (section - 1 < sortedUsersSectionsArray.size()) {
                    ArrayList<TLRPC.TL_contact> arr = usersSectionsDict.get(sortedUsersSectionsArray.get(section - 1));
                    if (position < arr.size()) {
                        return MessagesController.getInstance(currentAccount).getUser(arr.get(position).user_id);
                    }
                    return null;
                }
            }
        }
        if (needPhonebook) {
//            return ContactsController.getInstance(currentAccount).phoneBookContacts.get(position);
            return getPhoneBookContacts(UserPreference.getInt(UserPreference.IS_MX_USER, 0)).get(position);
        }
        return null;
    }

    @Override
    public boolean isEnabled(int section, int row) {
//        HashMap<String, ArrayList<TLRPC.TL_contact>> usersSectionsDict = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).usersMutualSectionsDict : ContactsController.getInstance(currentAccount).usersSectionsDict;
//        ArrayList<String> sortedUsersSectionsArray = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(currentAccount).sortedUsersSectionsArray;

        HashMap<String, ArrayList<TLRPC.TL_contact>> usersSectionsDict = getUsersSectionsDict(UserPreference.getInt(UserPreference.IS_MX_USER, 0));
        ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

        if (onlyUsers != 0 && !isAdmin) {
            ArrayList<TLRPC.TL_contact> arr = usersSectionsDict.get(sortedUsersSectionsArray.get(section));
            return row < arr.size();
        } else {
            if (section == 0) {
                if (needPhonebook || isAdmin) {
                    return row != 1;
                } else {
                    return row != 3;
                }
            } else if (section - 1 < sortedUsersSectionsArray.size()) {
                ArrayList<TLRPC.TL_contact> arr = usersSectionsDict.get(sortedUsersSectionsArray.get(section - 1));
                return row < arr.size();
            }
        }
        return true;
    }

    @Override
    public int getSectionCount() {
//        ArrayList<String> sortedUsersSectionsArray = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(currentAccount).sortedUsersSectionsArray;
        ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

        int count = sortedUsersSectionsArray.size();
        if (onlyUsers == 0) {
            count++;
        }
        if (isAdmin) {
            count++;
        }
        if (needPhonebook) {
            count++;
        }
        return count;
    }

    @Override
    public int getCountForSection(int section) {
//        HashMap<String, ArrayList<TLRPC.TL_contact>> usersSectionsDict = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).usersMutualSectionsDict : ContactsController.getInstance(currentAccount).usersSectionsDict;
//        ArrayList<String> sortedUsersSectionsArray = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(currentAccount).sortedUsersSectionsArray;

        HashMap<String, ArrayList<TLRPC.TL_contact>> usersSectionsDict = getUsersSectionsDict(UserPreference.getInt(UserPreference.IS_MX_USER, 0));
        ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

        if (onlyUsers != 0 && !isAdmin) {
            if (section < sortedUsersSectionsArray.size()) {
                ArrayList<TLRPC.TL_contact> arr = usersSectionsDict.get(sortedUsersSectionsArray.get(section));
                int count = arr.size();
                if (section != (sortedUsersSectionsArray.size() - 1) || needPhonebook) {
                    count++;
                }
                return count;
            }
        } else {
            if (section == 0) {
                if (needPhonebook || isAdmin) {
                    return 2;
                } else {
                    return 4;
                }
            } else if (section - 1 < sortedUsersSectionsArray.size()) {
                ArrayList<TLRPC.TL_contact> arr = usersSectionsDict.get(sortedUsersSectionsArray.get(section - 1));
                int count = arr.size();
                if (section - 1 != (sortedUsersSectionsArray.size() - 1) || needPhonebook) {
                    count++;
                }
                return count;
            }
        }
        if (needPhonebook) {
//            return ContactsController.getInstance(currentAccount).phoneBookContacts.size();
            return getPhoneBookContacts(UserPreference.getInt(UserPreference.IS_MX_USER, 0)).size();
        }
        return 0;
    }

    @Override
    public View getSectionHeaderView(int section, View view) {
//        ArrayList<String> sortedUsersSectionsArray = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(currentAccount).sortedUsersSectionsArray;
        ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

        if (view == null) {
            view = new LetterSectionCell(mContext);
        }
        LetterSectionCell cell = (LetterSectionCell) view;
        if (onlyUsers != 0 && !isAdmin) {
            if (section < sortedUsersSectionsArray.size()) {
                cell.setLetter(sortedUsersSectionsArray.get(section));
            } else {
                cell.setLetter("");
            }
        } else {
            if (section == 0) {
                cell.setLetter("");
            } else if (section - 1 < sortedUsersSectionsArray.size()) {
                cell.setLetter(sortedUsersSectionsArray.get(section - 1));
            } else {
                cell.setLetter("");
            }
        }
        return view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = new UserCell(mContext, 58, 1, false);
                break;
            case 1:
                view = new TextCell(mContext);
                break;
            case 2:
                view = new GraySectionCell(mContext);
                ((GraySectionCell) view).setText(LocaleController.getString("Contacts", R.string.Contacts).toUpperCase());
                break;
            case 3:
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
//                HashMap<String, ArrayList<TLRPC.TL_contact>> usersSectionsDict = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).usersMutualSectionsDict : ContactsController.getInstance(currentAccount).usersSectionsDict;
//                ArrayList<String> sortedUsersSectionsArray = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(currentAccount).sortedUsersSectionsArray;

                HashMap<String, ArrayList<TLRPC.TL_contact>> usersSectionsDict = getUsersSectionsDict(UserPreference.getInt(UserPreference.IS_MX_USER, 0));
                ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

                ArrayList<TLRPC.TL_contact> arr = usersSectionsDict.get(sortedUsersSectionsArray.get(section - (onlyUsers != 0 && !isAdmin ? 0 : 1)));
                TLRPC.User user = MessagesController.getInstance(currentAccount).getUser(arr.get(position).user_id);
                userCell.setData(user, null, null, 0);
                if (checkedMap != null) {
                    userCell.setChecked(checkedMap.indexOfKey(user.id) >= 0, !scrolling);
                }
                if (ignoreUsers != null) {
                    if (ignoreUsers.indexOfKey(user.id) >= 0) {
                        userCell.setAlpha(0.5f);
                    } else {
                        userCell.setAlpha(1.0f);
                    }
                }
                break;
            case 1:
                TextCell textCell = (TextCell) holder.itemView;
                if (section == 0) {
                    if (needPhonebook) {
                        textCell.setTextAndIcon(LocaleController.getString("InviteFriends", R.string.InviteFriends), R.drawable.menu_invite);
                    } else if (isAdmin) {
                        textCell.setTextAndIcon(LocaleController.getString("InviteToGroupByLink", R.string.InviteToGroupByLink), R.drawable.menu_invite);
                    } else {
                        if (position == 0) {
                            textCell.setTextAndIcon(LocaleController.getString("NewGroup", R.string.NewGroup), R.drawable.menu_newgroup);
                        } else if (position == 1) {
                            textCell.setTextAndIcon(LocaleController.getString("NewSecretChat", R.string.NewSecretChat), R.drawable.menu_secret);
                        } else if (position == 2) {
                            textCell.setTextAndIcon(LocaleController.getString("NewChannel", R.string.NewChannel), R.drawable.menu_broadcast);
                        }
                    }
                } else {
//                    ContactsController.Contact contact = ContactsController.getInstance(currentAccount).phoneBookContacts.get(position);
                    ContactsController.Contact contact = getPhoneBookContacts(UserPreference.getInt(UserPreference.IS_MX_USER, 0)).get(position);
                    if (contact.first_name != null && contact.last_name != null) {
                        textCell.setText(contact.first_name + " " + contact.last_name);
                    } else if (contact.first_name != null && contact.last_name == null) {
                        textCell.setText(contact.first_name);
                    } else {
                        textCell.setText(contact.last_name);
                    }
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int section, int position) {
//        HashMap<String, ArrayList<TLRPC.TL_contact>> usersSectionsDict = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).usersMutualSectionsDict : ContactsController.getInstance(currentAccount).usersSectionsDict;
//        ArrayList<String> sortedUsersSectionsArray = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(currentAccount).sortedUsersSectionsArray;

        HashMap<String, ArrayList<TLRPC.TL_contact>> usersSectionsDict = getUsersSectionsDict(UserPreference.getInt(UserPreference.IS_MX_USER, 0));
        ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

        if (onlyUsers != 0 && !isAdmin) {
            ArrayList<TLRPC.TL_contact> arr = usersSectionsDict.get(sortedUsersSectionsArray.get(section));
            return position < arr.size() ? 0 : 3;
        } else {
            if (section == 0) {
                if ((needPhonebook || isAdmin) && position == 1 || position == 3) {
                    return 2;
                }
            } else if (section - 1 < sortedUsersSectionsArray.size()) {
                ArrayList<TLRPC.TL_contact> arr = usersSectionsDict.get(sortedUsersSectionsArray.get(section - 1));
                return position < arr.size() ? 0 : 3;
            }
        }
        return 1;
    }

    @Override
    public String getLetter(int position) {
//        ArrayList<String> sortedUsersSectionsArray = onlyUsers == 2 ? ContactsController.getInstance(currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(currentAccount).sortedUsersSectionsArray;
        ArrayList<String> sortedUsersSectionsArray = getSortedUsersSectionsArray();

        int section = getSectionForPosition(position);
        if (section == -1) {
            section = sortedUsersSectionsArray.size() - 1;
        }
        if (section > 0 && section <= sortedUsersSectionsArray.size()) {
            return sortedUsersSectionsArray.get(section - 1);
        }
        return null;
    }

    @Override
    public int getPositionForScrollProgress(float progress) {
        return (int) (getItemCount() * progress);
    }

}
