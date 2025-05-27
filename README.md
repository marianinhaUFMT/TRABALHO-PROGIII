# Trabalho de Programação III

Esse trabalho foi feito como parte de nota parcial para a disciplina de Programação III, aqui estão os requisitos (note que o professor mandou um vídeo simulando o quiz, nosso trabalho era interpretar os requisitos a partir do video e implementar):

tela incial de login com campos para usuário e senha, com um botão para login e outro para cadastrar. na tela de cadastrar coloca-se o usuário e a senha e tem uma checkbox para caso queira se cadastrar como administrador, quando clica em cadastrar volta para a tela principal de login.

Se logar como adm, aparece uma tela com os botões:
- cadastrar nova questão
- visualizar questões
- ver ranking
- logout
- sair

ao clicar em cadastrar nova questão temos o campo para colocar o enunciado, e 4 campos para opção A, B, C e D, um botão para escolher qual a questão correta, um botão para escolher qual a dificuldade (Fácil, Intermediária, Difícil), um botão para Salvar e outro para Voltar.

ao clicar em Vizualizar questões, vemos uma tabela com o enunciado e dificuldade de todas as questões, com 3 botões embaixo: Editar, Excluir, Voltar

ao clicar em Ver ranking temos uma tabela com os usuários, pontuação e a data (yy-mm-dd hh-mm-ss) e um botão de voltar


quando um usuário (sem ser admin) faz o login, aparece uma tela com um botão para escolher a dificuldade e um botão iniciar quiz

após iniciar o quiz começa um timer de 60 segundos e aparece aleatoriamente uma pergunta com a dificuldade selecionada, com 4 alternativas para marcar e o botão próximo. após o usuário responder todas as perguntas que tiver daquela dificuldade, aparece um pop-up dizendo "Quis finalizado! "Você acertou x de y questões. Pontuação total: w pontos." e um botão OK. após dar ok, o quis retorna para a tela inicial.
