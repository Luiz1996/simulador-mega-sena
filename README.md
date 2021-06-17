# Simulador da Mega-Sena

## Descrição

```bash
Esta aplicação visa realizar uma simulação do jogo Mega-Sena da loteria Caixa.

O sistema é dividido em 3 sessões, são elas:

- Gerar Apostas: nesta funcionalidade o usuário poderá informar quantas apostas serão geradas
com números aleatórios a um determinado número de concurso. Para otimizar as inserções de 
apostas no banco de dados fiz uso de threads para inserções concorrentes.

- Validar Apostas: inicialmente serão informados os seis números vencedores e o número de concurso
a que esses números se refere, em seguida a aplicação apresentará em tela todas as combinações 
possíveis de acerto e então a aplicação disparará três threads para retornar quantas apostas 
acertaram os seis números, quina e quadra.

- Combinações Possíveis: dados seis números de entradas, esta funcionalidade apresentará ao usuário
todas as possibilidades de haver alguma premiação na Mega-Sena. Portanto, serão apresentados as 
vinte e duas maneiras de se acertar na mega.
```

## Configuração

```bash
A aplicação foi desenvolvida em Java e utilizando o banco de dados MySQL, caso tenha um banco MySQL
basta iniciar a aplicação e selecionar Alt + F2, esta funcionalidade criará o schema mega-sena do zero.

Alt + F2: criará/recriará toda a estrutura do banco de dados.

Bibliotecas externas: certifique-se de adicionar as libs externas anexadas a este projeto durante a 
importação do mesmo no NetBeans.

config.properties: este arquivo possui configurações importantes para o correto funcionamento da aplicação.
	- server: IP do servidor do banco de dados;
	- port: porta de comunicação com banco de dados;
	- schema: nome do schema do banco de dados, sugiro sempre usar 'mega-sena';
	- user: usuário que se autenticará no banco de dados;
	- password: senha do usuário de autenticação no banco de dados;
	- aposta_maior: o valor numérico atribuido a esta variável indicará que a cada X apostas inseridas, será
	  criada uma aposta com mais de seis números, simulando aqueles casos onde a pessoa paga a mais e pode 
	  escolher mais números em sua aposta para aumentar suas chances;
	- qtde_regs_commit: esta variável indica a quantos registros inseridos na base será realizado um commit.
```

## Executar Projeto

```bash
Passos para execução:
	
	- Clonar este repositório
	- Ter NetBeans 8.2(ou superior) instalado em seu computador
	- Importar o projeto em NetBeans / Arquivo(File) / Abrir Projeto(Open Project) / ...
	- Limpar e Contruir o projeto(Shift + F11)
	- Executar projeto(F6)
```