package com.hwqgooo.lifecycle;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;

import com.hwqgooo.lifecycle.databinding.ActivityMainBinding;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setModel(new SearchViewModel("hello world"));
        binding.setLifecycleOwner(this);
        getLifecycle().addObserver(new LifecycleObserverDemo());
        AndPermission.with(this)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        System.out.println(permissions);
                    }
                }).onDenied(
                new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        System.out.println(permissions);
                    }
                }).start();

    }

    private String[] getContacts() {
        //联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
        String[] arr = new String[cursor.getCount()];
        int i = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(0);
                //获取姓名
                String name = cursor.getString(1);
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                };
                arr[i] = id + " , 姓名：" + name;

                //根据联系人的ID获取此人的电话号码
                Cursor phonesCusor = this.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null,
                        null);

                //因为每个联系人可能有多个电话号码，所以需要遍历
                if (phonesCusor != null && phonesCusor.moveToFirst()) {
                    do {
                        String num = phonesCusor.getString(0);
                        arr[i] += " , 电话号码：" + num;
                    } while (phonesCusor.moveToNext());
                }
                i++;
            } while (cursor.moveToNext());
        }
        return arr;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        AndPermission.with(this)
//                .permission(Permission.Group.CONTACTS)
//                .rationale(new Rationale() {
//                    @Override
//                    public void showRationale(Context context, List<String> permissions, RequestExecutor executor) {
//                    }
//                })
//                .onGranted(new Action() {
//                    @Override
//                    public void onAction(List<String> permissions) {
//                        System.out.println(getContacts());
//                    }
//                }).onDenied(
//                new Action() {
//                    @Override
//                    public void onAction(List<String> permissions) {
//                        System.out.println(permissions);
//                    }
//                }).start();

    }
}
