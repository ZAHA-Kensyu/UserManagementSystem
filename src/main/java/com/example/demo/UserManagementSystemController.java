package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserManagementSystemController {
    //追加時
    @FXML
    TextField userName;

    @FXML
    TextField score;

    @FXML
    ComboBox affiliatedCompany;

    //編集時
    @FXML
    TextField userNameEdit;

    @FXML
    TextField scoreEdit;
    @FXML
    ComboBox affiliatedCompanyEdit;

    //テーブル
    @FXML
    TableView<UserInformation> userManagementSystemTable;

    @FXML
    TableColumn<UserInformation,Integer> idColumn;

    @FXML
    TableColumn<UserInformation,String> affiliatedCompanyColumn;

    @FXML
    TableColumn<UserInformation,String> nameColumn;

    @FXML
    TableColumn<UserInformation,Integer> scoreColumn;

    //社員を管理する リストを作成
    ObservableList<UserInformation> userInformationObservableList = FXCollections.observableArrayList();
    int id;
    public void initialize(){
        //コンボボックス
        affiliatedCompany.getItems().addAll("A会社","B会社","C会社","D会社");
        affiliatedCompanyEdit.getItems().addAll("A会社","B会社","C会社","D会社");

        //初期コンボボックス設定
        ObservableList<String> items = affiliatedCompany.getItems();
        affiliatedCompany.getSelectionModel().select(items.get(0));

        //初期テスト用データ登録
        userInformationObservableList.add(new UserInformation(1,"A会社","TEST",1));
        userManagementSystemTable.setItems(userInformationObservableList);

        //バインド設定
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        affiliatedCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("affiliatedCompany"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        //テーブルを選択した時の処理
        userManagementSystemTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String name = newSelection.getName();
                String affiliateCompany = newSelection.getAffiliatedCompany();
                int score = newSelection.getScore();

                //選択された行を編集可能エリアにセット
                userNameEdit.setText(name);
                scoreEdit.setText(String.valueOf(score));

                //コンボボックスの選択状態
                affiliatedCompanyEdit.getSelectionModel().select(affiliateCompany);
            }
        });

    }

    @FXML
    void updateButton(){
        if(!userManagementSystemTable.getSelectionModel().isEmpty()) {
            System.out.println("選択されている");
            userInformationObservableList.set(userManagementSystemTable.getSelectionModel().getSelectedIndex(), new UserInformation(3, affiliatedCompanyEdit.getValue().toString(), userNameEdit.getText(), RangController(Integer.parseInt(scoreEdit.getText()))));
            userManagementSystemTable.setItems(userInformationObservableList);
        }else{
            System.out.println("選択されていない");
        }
    }

    @FXML
    void deleteButton(){
        if(!userManagementSystemTable.getSelectionModel().isEmpty()) {
            //削除するデータが一つ以上なら削除可能
            if(userInformationObservableList.size() > 1) {
                userInformationObservableList.remove(userManagementSystemTable.getSelectionModel().getSelectedIndex());
            }
        }
    }

    @FXML
    void userAddButton(){
        id = userInformationObservableList.size() + 1;
        userInformationObservableList.add(new UserInformation(id,affiliatedCompany.getValue().toString(),userName.getText(),RangController(Integer.parseInt(score.getText()))));
        userManagementSystemTable.setItems(userInformationObservableList);
    }

    int RangController(int num){
        if(num > 100 || num < 0){
            num = 0;
        }

        return num;
    }
}
