package com.lulu.xutilsdemo;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lulu.xutilsdemo.model.Book;

import org.w3c.dom.Text;
import org.xutils.DbManager;
import org.xutils.config.DbConfigs;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.DbModel;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private static final String TAG = "BookActivity";
    @ViewInject(R.id.book_text_view)
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        x.view().inject(this);
        //xUtils 3 对象关系映射 orm
        DbManager.DaoConfig config = new DbManager.DaoConfig();
        config.setDbName("app.db").setDbVersion(1);
        DbManager db = x.getDb(config);

        Book book = new Book();
        book.setTitle("学会沉稳");
        book.setAuthor("changer0");
        book.setPrice(10000000000000000000f);
        try {

//            db.saveBindingId(book);
//            Snackbar.make(mTextView, "添加图书", Snackbar.LENGTH_SHORT).show();
            //查找所有book表中的记录, 并且形成 对象
            List<Book> books = db.findAll(Book.class);
            for (Book b : books) {
                Log.d(TAG, "onCreate: book: " + b.getId());
            }

            //按照条件查找
            SqlInfo sql = new SqlInfo("select * from books where _id > 2");
            List<DbModel> dbModelAll = db.findDbModelAll(sql);
            if (dbModelAll != null) {
                for (DbModel dbModel : dbModelAll) {
                    long id = dbModel.getLong("_id");
                    Log.d(TAG, "onCreate: id = " + id);
                }
            }
            //清空表中所有记录
            //db.delete(Book.class);
            //删除特定一个数据对象代表的对象
            //db.delete(book);
            // WhereBuild 可以定义负责的Where条件
            WhereBuilder b = WhereBuilder.b("_id", ">", 3);
            int n = db.delete(Book.class, b);
            if (n>0) {
                Snackbar.make(mTextView, "删除成功", Snackbar.LENGTH_SHORT).show();
            }

            // 更新


        } catch (DbException e) {
            e.printStackTrace();
        } finally {
            try {
                db.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
