package quiz.gui;

import quiz.dao.QuestaoDAO;
import quiz.dao.RankingDAO;
import quiz.dao.UsuarioDAO;
import quiz.model.Questao;
import quiz.model.Ranking;
import quiz.model.Usuario;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizApp extends Application {

    private Usuario loggedInUser;
    private Stage primaryStage;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final QuestaoDAO questaoDAO = new QuestaoDAO();
    private final RankingDAO rankingDAO = new RankingDAO();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginScreen();
    }

    private void showLoginScreen() {
        primaryStage.setTitle("Login");
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f4f8;");

        Label title = new Label("Login do Quiz");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Usuário");
        usernameField.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Senha");
        passwordField.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

        Button loginButton = new Button("Entrar");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        Button registerButton = new Button("Cadastrar");
        registerButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        registerButton.setOnMouseEntered(e -> registerButton.setStyle("-fx-background-color: #1e88e5; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        registerButton.setOnMouseExited(e -> registerButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            Usuario user = usuarioDAO.findByNomeAndSenha(username, password);
            if (user != null) {
                loggedInUser = user;
                if (user.isAdmin()) {
                    showAdminScreen();
                } else {
                    showUserScreen();
                }
            } else {
                showAlert("Erro", "Usuário ou senha incorretos.");
            }
        });

        registerButton.setOnAction(e -> showRegisterScreen());

        layout.getChildren().addAll(title, usernameField, passwordField, loginButton, registerButton);
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void showRegisterScreen() {
        primaryStage.setTitle("Cadastro");
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f4f8;");

        Label title = new Label("Cadastrar Usuário");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Usuário");
        usernameField.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Senha");
        passwordField.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

        CheckBox adminCheckBox = new CheckBox("Administrador");
        adminCheckBox.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");

        Button registerButton = new Button("Cadastrar");
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        registerButton.setOnMouseEntered(e -> registerButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        registerButton.setOnMouseExited(e -> registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        Button backButton = new Button("Voltar");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean isAdmin = adminCheckBox.isSelected();
            if (!username.isEmpty() && !password.isEmpty()) {
                if (!usuarioDAO.existsByNome(username)) {
                    usuarioDAO.salvar(new Usuario(username, password, isAdmin));
                    showAlert("Sucesso", "Usuário cadastrado com sucesso!");
                    showLoginScreen();
                } else {
                    showAlert("Erro", "Usuário já existe.");
                }
            } else {
                showAlert("Erro", "Preencha todos os campos.");
            }
        });

        backButton.setOnAction(e -> showLoginScreen());

        layout.getChildren().addAll(title, usernameField, passwordField, adminCheckBox, registerButton, backButton);
        Scene scene = new Scene(layout, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    private void showAdminScreen() {
        primaryStage.setTitle("Painel Administrador");
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f4f8;");

        Label title = new Label("Painel do Administrador");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Button addQuestionButton = new Button("Cadastrar Nova Questão");
        addQuestionButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        addQuestionButton.setOnMouseEntered(e -> addQuestionButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        addQuestionButton.setOnMouseExited(e -> addQuestionButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        Button viewQuestionsButton = new Button("Visualizar Questões");
        viewQuestionsButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        viewQuestionsButton.setOnMouseEntered(e -> viewQuestionsButton.setStyle("-fx-background-color: #1e88e5; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        viewQuestionsButton.setOnMouseExited(e -> viewQuestionsButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        Button viewRankingButton = new Button("Ver Ranking");
        viewRankingButton.setStyle("-fx-background-color: #FFC107; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        viewRankingButton.setOnMouseEntered(e -> viewRankingButton.setStyle("-fx-background-color: #e0a800; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        viewRankingButton.setOnMouseExited(e -> viewRankingButton.setStyle("-fx-background-color: #FFC107; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        Button exitButton = new Button("Sair");
        exitButton.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        exitButton.setOnMouseEntered(e -> exitButton.setStyle("-fx-background-color: #546E7A; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        exitButton.setOnMouseExited(e -> exitButton.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        addQuestionButton.setOnAction(e -> showAddQuestionScreen());
        viewQuestionsButton.setOnAction(e -> showViewQuestionsScreen());
        viewRankingButton.setOnAction(e -> showRankingScreen());
        logoutButton.setOnAction(e -> {
            loggedInUser = null;
            showLoginScreen();
        });
        exitButton.setOnAction(e -> {
            usuarioDAO.close();
            questaoDAO.close();
            rankingDAO.close();
            primaryStage.close();
        });

        layout.getChildren().addAll(title, addQuestionButton, viewQuestionsButton, viewRankingButton, logoutButton, exitButton);
        Scene scene = new Scene(layout, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    private void showAddQuestionScreen() {
        primaryStage.setTitle("Cadastrar Questão");
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f4f8;");

        Label title = new Label("Cadastrar Nova Questão");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextArea statementField = new TextArea();
        statementField.setPromptText("Enunciado da questão");
        statementField.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

        TextField optionA = new TextField();
        optionA.setPromptText("Opção A");
        optionA.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

        TextField optionB = new TextField();
        optionB.setPromptText("Opção B");
        optionB.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

        TextField optionC = new TextField();
        optionC.setPromptText("Opção C");
        optionC.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

        TextField optionD = new TextField();
        optionD.setPromptText("Opção D");
        optionD.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

        ComboBox<String> correctAnswerCombo = new ComboBox<>();
        correctAnswerCombo.getItems().addAll("A", "B", "C", "D");
        correctAnswerCombo.setPromptText("Resposta correta");
        correctAnswerCombo.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px;");

        ComboBox<String> difficultyCombo = new ComboBox<>();
        difficultyCombo.getItems().addAll("Fácil", "Intermediária", "Difícil");
        difficultyCombo.setPromptText("Dificuldade");
        difficultyCombo.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px;");

        Button saveButton = new Button("Salvar");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        saveButton.setOnMouseEntered(e -> saveButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        saveButton.setOnMouseExited(e -> saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        Button backButton = new Button("Voltar");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        saveButton.setOnAction(e -> {
            String statement = statementField.getText();
            String a = optionA.getText();
            String b = optionB.getText();
            String c = optionC.getText();
            String d = optionD.getText();
            String correct = correctAnswerCombo.getValue();
            String difficulty = difficultyCombo.getValue();

            if (!statement.isEmpty() && !a.isEmpty() && !b.isEmpty() && !c.isEmpty() && !d.isEmpty() && correct != null && difficulty != null) {
                questaoDAO.salvar(new Questao(statement, a, b, c, d, correct, difficulty));
                showAlert("Sucesso", "Questão cadastrada com sucesso!");
                showAdminScreen();
            } else {
                showAlert("Erro", "Preencha todos os campos.");
            }
        });

        backButton.setOnAction(e -> showAdminScreen());

        layout.getChildren().addAll(title, statementField, optionA, optionB, optionC, optionD, correctAnswerCombo, difficultyCombo, saveButton, backButton);
        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    private void showViewQuestionsScreen() {
        primaryStage.setTitle("Questões Cadastradas");
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f4f8;");

        TableView<Questao> table = new TableView<>();
        table.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px;");

        TableColumn<Questao, String> statementColumn = new TableColumn<>("Enunciado");
        statementColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEnunciado()));
        statementColumn.setPrefWidth(400);

        TableColumn<Questao, String> difficultyColumn = new TableColumn<>("Dificuldade");
        difficultyColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDificuldade()));
        difficultyColumn.setPrefWidth(150);

        // garante que somente essas colunas sejam exibidas
        table.getColumns().setAll(statementColumn, difficultyColumn);
        table.setItems(javafx.collections.FXCollections.observableArrayList(questaoDAO.listarTodos()));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button editButton = new Button("Editar");
        editButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        editButton.setOnMouseEntered(e -> editButton.setStyle("-fx-background-color: #1e88e5; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        editButton.setOnMouseExited(e -> editButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        Button deleteButton = new Button("Excluir");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        Button backButton = new Button("Voltar");
        backButton.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #546E7A; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        editButton.setOnAction(e -> {
            Questao selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showEditQuestionScreen(selected);
            } else {
                showAlert("Erro", "Selecione uma questão para editar.");
            }
        });

        deleteButton.setOnAction(e -> {
            Questao selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                questaoDAO.excluir(selected);
                table.getItems().remove(selected);
                showAlert("Sucesso", "Questão excluída com sucesso!");
            } else {
                showAlert("Erro", "Selecione uma questão para excluir.");
            }
        });

        backButton.setOnAction(e -> showAdminScreen());

        buttonBox.getChildren().addAll(editButton, deleteButton, backButton);
        layout.getChildren().addAll(table, buttonBox);
        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    private void showEditQuestionScreen(Questao questao) {
        primaryStage.setTitle("Editar Questão");
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f4f8;");

        TextArea statementField = new TextArea(questao.getEnunciado());
        statementField.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

        TextField optionA = new TextField(questao.getOpcaoA());
        optionA.setPromptText("Opção A");
        optionA.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

        TextField optionB = new TextField(questao.getOpcaoB());
        optionB.setPromptText("Opção B");
        optionB.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

        TextField optionC = new TextField(questao.getOpcaoC());
        optionC.setPromptText("Opção C");
        optionC.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

        TextField optionD = new TextField(questao.getOpcaoD());
        optionD.setPromptText("Opção D");
        optionD.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

        ComboBox<String> correctAnswerCombo = new ComboBox<>();
        correctAnswerCombo.getItems().addAll("A", "B", "C", "D");
        correctAnswerCombo.setValue(questao.getRespostaCorreta());
        correctAnswerCombo.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px;");

        ComboBox<String> difficultyCombo = new ComboBox<>();
        difficultyCombo.getItems().addAll("Fácil", "Intermediária", "Difícil");
        difficultyCombo.setValue(questao.getDificuldade());
        difficultyCombo.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px;");

        Button saveButton = new Button("Salvar");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        saveButton.setOnMouseEntered(e -> saveButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        saveButton.setOnMouseExited(e -> saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        Button backButton = new Button("Voltar");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        saveButton.setOnAction(e -> {
            questao.setOpcaoA(optionA.getText());
            questao.setOpcaoB(optionB.getText());
            questao.setOpcaoC(optionC.getText());
            questao.setOpcaoD(optionD.getText());
            questao.setRespostaCorreta(correctAnswerCombo.getValue());
            questao.setDificuldade(difficultyCombo.getValue());
            questaoDAO.atualizar(questao);
            showAlert("Sucesso", "Questão atualizada com sucesso!");
            showViewQuestionsScreen();
        });

        backButton.setOnAction(e -> showViewQuestionsScreen());

        layout.getChildren().addAll(statementField, optionA, optionB, optionC, optionD, correctAnswerCombo, difficultyCombo, saveButton, backButton);
        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen(); 
    }

    private void showRankingScreen() {
        primaryStage.setTitle("Ranking");
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f4f8;");

        TableView<Ranking> table = new TableView<>();
        table.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px;");

        TableColumn<Ranking, String> userColumn = new TableColumn<>("Usuário");
        userColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsuario().getNome()));
        userColumn.setPrefWidth(200);

        TableColumn<Ranking, Integer> scoreColumn = new TableColumn<>("Pontuação");
        scoreColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getPontuacao()).asObject());
        scoreColumn.setPrefWidth(150);

        TableColumn<Ranking, String> dateColumn = new TableColumn<>("Data");
        dateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getData().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        dateColumn.setPrefWidth(200);

        table.getColumns().setAll(userColumn, scoreColumn, dateColumn); // garante que apenas essas colunas sejam exibidas
        table.setItems(javafx.collections.FXCollections.observableArrayList(rankingDAO.listarTodos()));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button backButton = new Button("Voltar");
        backButton.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #546E7A; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        backButton.setOnAction(e -> showAdminScreen());

        layout.getChildren().addAll(table, backButton);
        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen(); 
    }

    private void showUserScreen() {
        primaryStage.setTitle("Quiz");
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f4f8;");

        Label title = new Label("Bem-vindo, " + loggedInUser.getNome());
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");

        ComboBox<String> difficultyCombo = new ComboBox<>();
        difficultyCombo.getItems().addAll("Fácil", "Intermediária", "Difícil");
        difficultyCombo.setPromptText("Escolha a dificuldade");
        difficultyCombo.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-radius: 5px;");

        Button startQuizButton = new Button("Iniciar Quiz");
        startQuizButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        startQuizButton.setOnMouseEntered(e -> startQuizButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        startQuizButton.setOnMouseExited(e -> startQuizButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        
        Button backButton = new Button("Voltar");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        
        backButton.setOnAction(e -> showLoginScreen());

        startQuizButton.setOnAction(e -> {
            String difficulty = difficultyCombo.getValue();
            if (difficulty != null) {
                startQuiz(difficulty);
            } else {
                showAlert("Erro", "Selecione uma dificuldade.");
            }
        });

        layout.getChildren().addAll(title, difficultyCombo, startQuizButton, backButton);
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen(); 
    }

    private void startQuiz(String difficulty) {
        primaryStage.setTitle("Quiz - " + difficulty);
        List<Questao> quizQuestions = new ArrayList<>(questaoDAO.findByDificuldade(difficulty));
        if (quizQuestions.isEmpty()) {
            showAlert("Erro", "Nenhuma questão disponível para essa dificuldade.");
            return;
        }
        Collections.shuffle(quizQuestions);

        final int[] currentIndex = {0};
        final int[] correctAnswers = {0};
        final int totalQuestions = quizQuestions.size();

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f4f8;");

        Label timerLabel = new Label("Tempo: 60s");
        timerLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");

        Label questionLabel = new Label(quizQuestions.get(0).getEnunciado());
        questionLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333; -fx-wrap-text: true; -fx-alignment: center;");
        questionLabel.setMaxWidth(350);
        questionLabel.setAlignment(Pos.CENTER); // centraliza o texto da questao

        // VBox para as alternativas
        VBox optionsBox = new VBox(5);
        optionsBox.setAlignment(Pos.CENTER_LEFT); 
        optionsBox.setPadding(new Insets(0, 0, 0, 20)); 

        RadioButton optionA = new RadioButton(quizQuestions.get(0).getOpcaoA());
        optionA.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");

        RadioButton optionB = new RadioButton(quizQuestions.get(0).getOpcaoB());
        optionB.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");

        RadioButton optionC = new RadioButton(quizQuestions.get(0).getOpcaoC());
        optionC.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");

        RadioButton optionD = new RadioButton(quizQuestions.get(0).getOpcaoD());
        optionD.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");

        optionsBox.getChildren().addAll(optionA, optionB, optionC, optionD);

        ToggleGroup group = new ToggleGroup();
        optionA.setToggleGroup(group);
        optionB.setToggleGroup(group);
        optionC.setToggleGroup(group);
        optionD.setToggleGroup(group);

        Button nextButton = new Button("Próximo");
        nextButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        nextButton.setOnMouseEntered(e -> nextButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));
        nextButton.setOnMouseExited(e -> nextButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;"));

        final int[] timeLeft = {60};
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1), e -> {
                    timeLeft[0]--;
                    timerLabel.setText("Tempo: " + timeLeft[0] + "s");
                    if (timeLeft[0] <= 0) {
                        Platform.runLater(() -> endQuiz(correctAnswers[0], totalQuestions, difficulty));
                    }
                })
        );
        timeline.setCycleCount(60);
        timeline.play();

        nextButton.setOnAction(e -> {
            RadioButton selected = (RadioButton) group.getSelectedToggle();
            if (selected != null) {
                String answer = selected.getText().equals(quizQuestions.get(currentIndex[0]).getOpcaoA()) ? "A" :
                        selected.getText().equals(quizQuestions.get(currentIndex[0]).getOpcaoB()) ? "B" :
                                selected.getText().equals(quizQuestions.get(currentIndex[0]).getOpcaoC()) ? "C" : "D";
                if (answer.equals(quizQuestions.get(currentIndex[0]).getRespostaCorreta())) {
                    correctAnswers[0]++;
                }
                currentIndex[0]++;
                if (currentIndex[0] < quizQuestions.size()) {
                    questionLabel.setText(quizQuestions.get(currentIndex[0]).getEnunciado());
                    optionA.setText(quizQuestions.get(currentIndex[0]).getOpcaoA());
                    optionB.setText(quizQuestions.get(currentIndex[0]).getOpcaoB());
                    optionC.setText(quizQuestions.get(currentIndex[0]).getOpcaoC());
                    optionD.setText(quizQuestions.get(currentIndex[0]).getOpcaoD());
                    group.getToggles().forEach(toggle -> ((RadioButton) toggle).setSelected(false));
                } else {
                    timeline.stop();
                    endQuiz(correctAnswers[0], totalQuestions, difficulty);
                }
            } else {
                showAlert("Erro", "Selecione uma alternativa.");
            }
        });

        layout.getChildren().addAll(timerLabel, questionLabel, optionsBox, nextButton);
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen(); 
    }

    private void endQuiz(int correctAnswers, int totalQuestions, String difficulty) {
        int pointsPerQuestion;
        switch (difficulty) {
            case "Fácil":
                pointsPerQuestion = 1;
                break;
            case "Intermediária":
                pointsPerQuestion = 2;
                break;
            case "Difícil":
                pointsPerQuestion = 3;
                break;
            default:
                pointsPerQuestion = 0;
        }
        int score = correctAnswers * pointsPerQuestion;

        // verifica se o usuario ja tem uma entidade no ranking
        Ranking existingRanking = rankingDAO.findByUsuario(loggedInUser);
        if (existingRanking != null) {
            // atualizar a pontuacao existente
            existingRanking.setPontuacao(existingRanking.getPontuacao() + score);
            existingRanking.setData(LocalDateTime.now());
            
            rankingDAO.atualizar(existingRanking);
        } else {
            // criar uma nova entidade no ranking
            rankingDAO.salvar(new Ranking(loggedInUser, score, LocalDateTime.now()));
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Finalizado");
        alert.setHeaderText(null);
        alert.setContentText("Você acertou " + correctAnswers + " de " + totalQuestions + " questões.\nPontuação total: " + score + " pontos.");
        alert.showAndWait();

        showUserScreen();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}