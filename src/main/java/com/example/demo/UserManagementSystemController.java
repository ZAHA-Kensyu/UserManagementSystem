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

    @FXML
    Label inputError;

    @FXML
    Label rangeError;

    @FXML
    Label inputError1;

    @FXML
    Label editInputError;

    @FXML
    Label editRangeError;

    @FXML
    Label editInputError1;

    //社員を管理する リストを作成
    ObservableList<UserInformation> userInformationObservableList = FXCollections.observableArrayList();
    int id;
    public void initialize(){
        errorShow();

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

    void errorShow(){
        inputError.setOpacity(0);
        rangeError.setOpacity(0);
        inputError1.setOpacity(0);
        editInputError.setOpacity(0);
        editRangeError.setOpacity(0);
        editInputError1.setOpacity(0);
    }

    @FXML
    void updateButton(){
        errorShow();
        if(!userManagementSystemTable.getSelectionModel().isEmpty()) {
            //空白が入力されたら
            boolean addFlg = true;
            String name = userNameEdit.getText();
            String aff = affiliatedCompanyEdit.getValue().toString();
            String scoreText = scoreEdit.getText();
            int score = 0;
            if(!isCheck(scoreText)){
                System.out.println("数値ではないエラー出力");
                editInputError1.setOpacity(1);
                addFlg = false;
            }else{
                if(scoreText == "" || scoreText==" "){
                    System.out.println("数値入力空白エラー");
                    editInputError1.setOpacity(1);
                    addFlg = false;
                }else{
                    score = Integer.parseInt(scoreText);
                    if(!rangCheck(score)){
                        System.out.println("入力が範囲外です");
                        editRangeError.setOpacity(1);
                        addFlg = false;
                    }
                }

            }

            if(name == "" || name == " "){
                //エラー処理
                System.out.println("名前欄が空白なのでエラー");
                editInputError.setOpacity(1);
                addFlg = false;
            }

            if(addFlg) {
                System.out.println("選択されている");
                userInformationObservableList.set(userManagementSystemTable.getSelectionModel().getSelectedIndex(), new UserInformation(1, aff, name, score));
                userManagementSystemTable.setItems(userInformationObservableList);
            }

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
        errorShow();
            //空白が入力されたら
        boolean addFlg = true;
        String name = userName.getText();
        String aff = affiliatedCompany.getValue().toString();
        String scoreText = score.getText();
        int score = 0;
        if(!isCheck(scoreText)){
            System.out.println("数値ではないエラー出力");
            inputError1.setOpacity(1);
            addFlg = false;
        }else{
            if(scoreText == "" || scoreText==" "){
                System.out.println("数値入力空白エラー");
                inputError1.setOpacity(1);
                addFlg = false;
            }else{
                score = Integer.parseInt(scoreText);
                if(!rangCheck(score)){
                    System.out.println("入力が範囲外です");
                    rangeError.setOpacity(1);
                    addFlg = false;
                }
            }

        }

        if(name == "" || name == " "){
                //エラー処理
            System.out.println("名前欄が空白なのでエラー");
            inputError.setOpacity(1);
            addFlg = false;
        }

        if(addFlg){
            id = userInformationObservableList.size() + 1;
            userInformationObservableList.add(new UserInformation(id, aff, name, score));
            userManagementSystemTable.setItems(userInformationObservableList);
        }
    }

    boolean isCheck(String str){
        boolean isNumber = true;

        for(var i = 0; i < str.length(); i++){
            if(!Character.isDigit(str.charAt(i))){
                isNumber = false;
                break;
            }
        }

        return isNumber;
    }

    boolean rangCheck(int num){
        boolean isRange= true;

        if(num > 100 || num < 0){
            isRange = false;
        }

        return isRange;
    }
}
