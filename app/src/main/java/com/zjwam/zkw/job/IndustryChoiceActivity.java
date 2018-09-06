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
import com.zjwam.zkw.entity.IndustryChoiceBean;
import com.zjwam.zkw.entity.IndustrySelectBean;
import com.zjwam.zkw.mvp.presenter.IndustryChoicePresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IIndustryChoicePresenter;
import com.zjwam.zkw.mvp.view.IIndustryChoiceView;
import com.zjwam.zkw.util.KeyboardUtils;
import com.zjwam.zkw.util.IndustryNodeViewFactory;

import java.util.ArrayList;
import java.util.List;

import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;

public class IndustryChoiceActivity extends BaseActivity implements IIndustryChoiceView{

    private ImageView back;
    private TextView title,industry_choice_over;
    private EditText industry_search_text;
    private RelativeLayout industry_choice_relative;
    private FlowLayout industry_flowLayout;
    private TreeNode root;
    private TreeView treeView;
    private IIndustryChoicePresenter industryChoicePresenter;
    private List<String> selectItem;
    private String searchName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_choice);
        initView();
        initData();
    }

    private void initData() {
        industryChoicePresenter = new IndustryChoicePresenter(this,this);
        selectItem = new ArrayList<>();
        back.setVisibility(View.GONE);
        title.setText("切换行业");
        industryChoicePresenter.getIdustry(searchName);
        industry_choice_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("hyid", getSelectedIds());
                intent.putExtra("hy",getSelectedText());
                setResult(1, intent);
                finish();
            }
        });
        industry_search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                searchName = industry_search_text.getText().toString();
                if (i == EditorInfo.IME_ACTION_SEARCH && searchName.trim().length()>0){
                    industryChoicePresenter.getIdustry(searchName);
                    KeyboardUtils.hideKeyboard(industry_search_text);
                }
                return false;
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        industry_choice_over = findViewById(R.id.industry_choice_over);
        industry_search_text = findViewById(R.id.industry_search_text);
        industry_choice_relative = findViewById(R.id.industry_choice_relative);
        industry_flowLayout = findViewById(R.id.industry_flowLayout);
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
    public void setIndustry(List<IndustryChoiceBean> list) {
        industry_choice_relative.removeAllViews();
        root = TreeNode.root();
        for (int i=0;i<list.size();i++){
            TreeNode treeNode = new TreeNode(new String(list.get(i).getName()));
            treeNode.setLevel(0);
            for (int j=0;j<list.get(i).getChild().size();j++){
                IndustrySelectBean industrySelect = new IndustrySelectBean();
                industrySelect.setName(list.get(i).getChild().get(j).getName());
                industrySelect.setCate(list.get(i).getChild().get(j).getCate());
                TreeNode treeNode1 = new TreeNode(industrySelect);
                treeNode1.setLevel(1);
                treeNode.addChild(treeNode1);
            }
            root.addChild(treeNode);
        }
        IndustryNodeViewFactory industryNodeViewFactory = new IndustryNodeViewFactory();
        industryNodeViewFactory.setCheckBoxSelector(new IndustryNodeViewFactory.CheckBoxSelector() {
            @Override
            public void selector(String selectorText, boolean isChecked) {
                industry_flowLayout.removeAllViews();
                if (isChecked){
                    selectItem.add(selectorText);
                }else {
                    selectItem.remove(selectorText);
                }
                for (String item:selectItem){
                    TextView checkboxView = (TextView) LayoutInflater.from(getBaseContext()).inflate(R.layout.industry_selector, industry_flowLayout,false);
                    checkboxView.setText(item);
                    industry_flowLayout.addView(checkboxView);
                }
            }
        });
        treeView = new TreeView(root, this, industryNodeViewFactory);
        View view = treeView.getView();
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        industry_choice_relative.addView(view);
        treeView.expandAll();
    }

    @Override
    public void showMsg(String msg) {
        error(msg);
    }
}
