package com.zjwam.zkw.job;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.R;
import com.zjwam.zkw.customview.FlowLayout;
import com.zjwam.zkw.entity.IndustrySelectBean;
import com.zjwam.zkw.entity.ProfessionChoiceBean;
import com.zjwam.zkw.mvp.presenter.ProfessionChoicePresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IProfessionChoicePresenter;
import com.zjwam.zkw.mvp.view.IProfessionChoiceView;
import com.zjwam.zkw.util.KeyboardUtils;
import com.zjwam.zkw.util.ProfessionNodeViewFactory;

import java.util.ArrayList;
import java.util.List;

import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;

public class ProfessionChoiceActivity extends BaseActivity implements IProfessionChoiceView{

    private ImageView back;
    private TextView title,profession_choice_over;
    private EditText profession_search_text;
    private RelativeLayout profession_choice_relative;
    private FlowLayout profession_flowLayout;
    private TreeNode root;
    private TreeView treeView;
    private IProfessionChoicePresenter professionChoicePresenter;
    private List<String> selectItem;
    private String searchName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession_choice);
        initView();
        initData();
    }

    private void initData() {
        back.setVisibility(View.GONE);
        title.setText("切换职位");
        professionChoicePresenter = new ProfessionChoicePresenter(this,this);
        selectItem = new ArrayList<>();
        professionChoicePresenter.getProfession(searchName);
        profession_choice_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("zyid", getSelectedIds());
                intent.putExtra("zy",getSelectedText());
                setResult(2, intent);
                finish();
            }
        });
        profession_search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                searchName = profession_search_text.getText().toString();
                if (i == EditorInfo.IME_ACTION_SEARCH && searchName.trim().length()>0){
                    professionChoicePresenter.getProfession(searchName);
                    KeyboardUtils.hideKeyboard(profession_search_text);
                }
                return false;
            }
        });

    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        profession_choice_over = findViewById(R.id.profession_choice_over);
        profession_search_text = findViewById(R.id.profession_search_text);
        profession_choice_relative = findViewById(R.id.profession_choice_relative);
        profession_flowLayout = findViewById(R.id.profession_flowLayout);
    }

    private String getSelectedIds() {
        StringBuilder stringBuilder = new StringBuilder();
        List<TreeNode> selectedIds = treeView.getSelectedNodes();
        for (int i = 0; i < selectedIds.size(); i++) {
            if (selectedIds.get(i).getValue() instanceof IndustrySelectBean){
                stringBuilder.append(((IndustrySelectBean)selectedIds.get(i).getValue()).getCate() + "_");
            }
        }
        return stringBuilder.toString();
    }
    private String getSelectedText() {
        StringBuilder stringBuilder = new StringBuilder();
        List<TreeNode> selectedText = treeView.getSelectedNodes();
        for (int i = 0; i < selectedText.size(); i++) {
            if (selectedText.get(i).getValue() instanceof IndustrySelectBean){
                stringBuilder.append(((IndustrySelectBean)selectedText.get(i).getValue()).getName() + "/");
            }
        }
        String result = stringBuilder.toString();
        if (result.length()>0){
            result  = result.substring(0,result.length() - 1);
        }
        return result;
    }

    @Override
    public void setProfession(List<ProfessionChoiceBean> list) {
        profession_choice_relative.removeAllViews();
        root = TreeNode.root();
        for (int i=0;i<list.size();i++){
            TreeNode treeNode = new TreeNode(new String(list.get(i).getName()));
            treeNode.setLevel(0);
            for (int j=0;j<list.get(i).getChild().size();j++){
                TreeNode treeNode1 = new TreeNode(new String(list.get(i).getChild().get(j).getName()));
                treeNode1.setLevel(1);
                for (int k=0;k<list.get(i).getChild().get(j).getChild().size();k++){
                    IndustrySelectBean industrySelect = new IndustrySelectBean();
                    industrySelect.setName(list.get(i).getChild().get(j).getChild().get(k).getName());
                    industrySelect.setCate(list.get(i).getChild().get(j).getChild().get(k).getCate());
                    TreeNode treeNode2 = new TreeNode(industrySelect);
                    treeNode1.addChild(treeNode2);
                    treeNode2.setLevel(2);
                }
                treeNode.addChild(treeNode1);
            }
            root.addChild(treeNode);
        }
        ProfessionNodeViewFactory professionNodeViewFactory = new ProfessionNodeViewFactory();
        professionNodeViewFactory.setCheckBoxSelector(new ProfessionNodeViewFactory.CheckBoxSelector() {
            @Override
            public void selector(String selectorText, boolean isChecked) {
                profession_flowLayout.removeAllViews();
                if (isChecked){
                    selectItem.add(selectorText);
                }else {
                    selectItem.remove(selectorText);
                }
                for (String item:selectItem){
                    TextView checkboxView = (TextView) LayoutInflater.from(getBaseContext()).inflate(R.layout.industry_selector, profession_flowLayout,false);
                    checkboxView.setText(item);
                    profession_flowLayout.addView(checkboxView);
                }
            }
        });
        treeView = new TreeView(root, this, professionNodeViewFactory);
        View view = treeView.getView();
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        profession_choice_relative.addView(view);
        treeView.expandAll();
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }
}
