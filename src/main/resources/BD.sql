--Criar o Banco de Dados com o nome de LoCarSys
--Nome do usuário: app
--Senha: app 
CREATE TABLE Filial(
id_Filial bigint not null generated always
        as identity (start with 1, increment by 1)
        constraint PK_Filial PRIMARY KEY,
Nome_Filial varchar(50) not null,
Estado_Filial varchar(2) not null,
Cidade_Filial varchar(40) not null
);

CREATE TABLE Funcionario(
id_Funcionario bigint not null generated always 
        as identity (start with 1, increment by 1)
        constraint PK_Funcionario PRIMARY KEY,
id_Filial bigint not null,
Nome_Funcionario varchar(40) not null,
Data_Nasc_Funcionario date not null,
CPF_Funcionario  char(14) not null,
Funcao_Funcionario varchar(30) not null,
Sexo_Funcionario char(1) not null,
constraint fk_id_Filial foreign key (id_Filial) references Filial
);

CREATE TABLE Usuario(
id_Usuario bigint not null generated always
        as identity (start with 1, increment by 1)
        constraint PK_Usuario PRIMARY KEY,
id_Funcionario bigint not null,
Login_Usuario varchar(15) not null,
Senha_Usuario varchar(10) not null,
Tipo_Usuario  varchar(1) not null,
constraint fk_id_Funcionario foreign key (id_Funcionario) references Funcionario
);

CREATE TABLE Fabricante(
id_Fabricante bigint not null generated always
        as identity (start with 1, increment by 1)
        constraint PK_Fabricante PRIMARY KEY,
Nome_Fabricante varchar(20) not null
);

CREATE TABLE Combustivel(
id_Combustivel bigint not null generated always
        as identity (start with 1, increment by 1)
        constraint PK_Combustivel PRIMARY KEY,
Tipo_Combustivel varchar(10) not null
);

CREATE TABLE Classe(
id_Classe bigint not null generated always
        as identity (start with 1, increment by 1)
        constraint PK_Classe PRIMARY KEY,
Tipo_Classe varchar(1) not null,
Tarifa_Classe float not null
);

CREATE TABLE Cliente(
id_Cliente bigint not null generated always
        as identity (start with 1, increment by 1)
        constraint PK_Cliente PRIMARY KEY,
Nome_Cliente varchar(50) not null,
CPF_Cliente char(14) not null,
CNH_Cliente char(11) not null,
Data_Nasc_Cliente date not null,
Sexo_Cliente char(1) not null,
Celular_Cliente varchar(14),
Email_Cliente varchar(50)
);

CREATE TABLE Endereco(
id_Endereco bigint not null generated always
        as identity (start with 1, increment by 1)
        constraint PK_Endereco PRIMARY KEY,
id_Cliente bigint not null,
Logradouro_Endereco varchar(50) not null,
Numero_Endereco varchar(5) not null,
Bairro_Endereco varchar(30) not null,
Complemento_Endereco varchar(30),
Cep_Endereco varchar(9) not null,
Obs_Endereco varchar(50),
constraint fk_id_Cliente foreign key (id_Cliente) references Cliente
);

CREATE TABLE Carro(
id_Carro bigint not null generated always
        as identity (start with 1, increment by 1)
        constraint PK_Carro PRIMARY KEY,
id_Filial bigint not null,
id_Classe bigint not null,
id_Fabricante bigint not null,
id_Combustivel bigint not null,
Ano_Fabricacao_Carro char(4) not null,             
Chassi_Carro char(22) not null,
Cor_Carro varchar(30) not null,
Modelo_Carro varchar(30) not null,
Placa_Carro varchar(8) not null,
Estado_Carro varchar(2) not null,
Cidade_Carro varchar(40) not null,
Disponibilidade_Carro char(1) not null,
Ano_Carro char(4) not null,
Renavam_Carro varchar(12) not null,
Kilometragem_Carro float not null,
constraint fk_id_Nome_Filial foreign key (id_Filial) references Filial,
constraint fk_id_Classe foreign key (id_Classe) references Classe,
constraint fk_id_Fabricante foreign key (id_Fabricante) references Fabricante,
constraint fk_id_Combustivel foreign key (id_Combustivel) references Combustivel
);

CREATE TABLE Estoque(
id_Estoque bigint not null generated always
        as identity (start with 1, increment by 1)
        constraint PK_Estoque PRIMARY KEY,
id_Classe bigint not null,                 
Quantidade_Estoque varchar(3) not null,
constraint fk_id_Classe_Estoque foreign key (id_Classe) references Classe
);

CREATE TABLE Aluguel(
id_Aluguel bigint not null generated always
        as identity (start with 1, increment by 1)
        constraint PK_Aluguel PRIMARY KEY,
id_Funcionario bigint not null,
id_Carro bigint not null,
id_Cliente bigint not null,                   
Data_Locacao_Aluguel date not null,
Data_Devolucao_Aluguel date not null,
Preco_Total float not null,
constraint fk_id_Funcionario_Aluguel foreign key (id_Funcionario) references Funcionario,
constraint fk_id_Carro foreign key (id_Carro) references Carro,
constraint fk_id_Cliente_Aluguel foreign key (id_Cliente) references Cliente
);

--Inserir Valores na Tabela Filial
INSERT INTO Filial (Nome_Filial, Estado_Filial, Cidade_Filial)
values 
('Nações Unidas','SP','São Paulo'),
('Copacabana','RJ','Rio de Janeiro'),
('Belvedere','MG','Belo Horizonte'),
('Baixada Santista','SP','São Paulo');

SELECT * FROM Filial;

--Inserir Valores na Tabela Funcionario
INSERT INTO Funcionario (ID_FILIAL, NOME_FUNCIONARIO, DATA_NASC_FUNCIONARIO, CPF_FUNCIONARIO, FUNCAO_FUNCIONARIO, SEXO_FUNCIONARIO)
values 
(1, 'Fabio Sousa','1985-08-17','345.275.438-30','Gerente','M'),
(2, 'Bruno Costa','1995-12-13','456.789.564-32','Atendente','M'),
(3, 'Vinicius Viana','1995-04-30','123.456.789-00','TI','M');

SELECT * FROM Funcionario;

--Inserir Valores na Tabela Usúario, declarando como chave estrangeira--------------------------------------------------------
INSERT INTO USUARIO (ID_FUNCIONARIO,LOGIN_USUARIO,SENHA_USUARIO,TIPO_USUARIO)
VALUES 
(1,'Fabio23','1234','G'),
(2,'Bruno008','4321','V'),
(3,'Vinicius1995','1111','T');

SELECT * FROM Usuario;

--Inserir Valores na Tabela Fabricante-----------------------------------------------------------------------------------------------
INSERT INTO Fabricante(Nome_Fabricante)
values
('Chevrolet'),
('Ford'),
('Fiat'),
('Renault'),
('Volkswagen');

SELECT * FROM Fabricante;

--Inserir Valores na Tabela Combustivel------------------------------------------------------------------------------------------------
INSERT INTO Combustivel(Tipo_Combustivel)
values
('Gasolina'),
('Álcool'),
('Flex'),
('GNV');

SELECT * FROM Combustivel;

--Inserir Valores na Tabela Classe----------------------------------------------------------------------------------------------------
INSERT INTO Classe(Tipo_Classe,Tarifa_Classe)
values
('A',39.90),
('B',75.90),                                                                                   
('C',97.90),
('D',107.90),
('E',211.90);
 
SELECT * FROM Classe
ORDER BY Tipo_Classe asc;

--Inserir Valores na Tabela Cliente------------------------------------------------------------------------------------------------
INSERT INTO Cliente (Nome_Cliente,CPF_Cliente,CNH_Cliente,Data_Nasc_Cliente,Sexo_Cliente)
values
('Vanessa Ribeiro','123.456.789-01','98765432111','1986-12-23','F'),
('Carol Viana','123.456.789-02','98765432112','1995-04-05','F'),
('Bruna Costa','123.456.789-03','98765432113','1989-12-12','F');


SELECT * FROM Cliente;

--Inserir Valores na Tabela Endereco, declarando o @id_Cliente como chave estrangeira------------------------------------------------------
INSERT INTO Endereco
(id_Cliente,Logradouro_Endereco,Numero_Endereco,Bairro_Endereco,Complemento_Endereco,Cep_Endereco)
VALUES
(1,'Rua Sai-Guacu','309','PQ.São Jose','Casa 1','04843-309'),
(2,'Av. Senador Teotonio Vilela','10','PQ.Grajaú','Bloco A Ap 12 ','04833-901'),
(3,'Av. Senador Teotonio Vilela','11','PQ.Grajaú','Bloco B Ap 13','04833-901');

SELECT * FROM Endereco;

--Inserir Valores na Tabela Carro, declarando os @id_Classe,@id_Fabricante e @id_Combustivel como chaves estrangeiras------------------------
INSERT INTO Carro
(id_Filial,id_Classe,id_Fabricante,id_Combustivel,Ano_Fabricacao_Carro,Chassi_Carro,Cor_Carro,Modelo_Carro,Placa_Carro,Estado_Carro, Cidade_Carro,
Disponibilidade_Carro,Ano_Carro,Renavam_Carro,Kilometragem_Carro)
values
(1,1,1,1,'2000','22.33.55555.1.1.666666','Prata','Celta','FAB-7894','SP','São Paulo','1','2013','1234567890-9',1000);

SELECT * FROM Carro;

--Inserir Valores na Tabela Estoque, declarando o @id_Classe como chave estrangeira-------------------------------------------------------------------------
INSERT INTO Estoque(id_Classe,Quantidade_Estoque)
values
(1,'20');


SELECT * FROM Estoque;

--Inserir Valores na Tabela Aluguel, declarando os @id_Funcionario, @id_Classe, @id_Carro e @id_Cliente como chaves estrangeiras----------------------------
INSERT INTO Aluguel(id_Funcionario,id_Carro,id_Cliente,Data_Locacao_Aluguel,Data_Devolucao_Aluguel, Preco_Total)
VALUES
(1,1,1,'2015-06-01','2015-06-02',39.9);


SELECT * FROM Aluguel;

                                          -- CLASSE B --1
--Inserir Valores na Tabela Carro, declarando os @id_Classe,@id_Fabricante e @id_Combustivel como chaves estrangeiras------------------------
INSERT INTO Carro
(id_Filial,id_Classe,id_Fabricante,id_Combustivel,Ano_Fabricacao_Carro,Chassi_Carro,Cor_Carro,Modelo_Carro,Placa_Carro,Estado_Carro,
Cidade_Carro,Disponibilidade_Carro,Ano_Carro,Renavam_Carro,Kilometragem_Carro) values
(1,2,2,3,'2008','47.45.46556.4.9.462947','Prata','Ford Ka','FOR-8894','RJ','Angra dos Reis','1','2014','4564695918-2',2000),
                                          -- CLASSE B --2
--Inserir Valores na Tabela Carro, declarando os @id_Classe,@id_Fabricante e @id_Combustivel como chaves estrangeiras------------------------
(2,2,4,4,'2007','45.65.56465.6.5.413566','Prata','Renault Sandero','RSR-9994','SP','São Paulo','1','2009','5182862534-5',2500),
                                          -- CLASSE B --3
--Inserir Valores na Tabela Carro, declarando os @id_Classe,@id_Fabricante e @id_Combustivel como chaves estrangeiras------------------------
(3,2,5,3,'1980','64.59.62659.4.5.616529','Branco','Volkswagen Gol','VSG-2394','SP','São Paulo','1','2015','3234567890-8',3500),
                                          -- CLASSE C --1
--Inserir Valores na Tabela Carro, declarando os @id_Classe,@id_Fabricante e @id_Combustivel como chaves estrangeiras------------------------
(4,3,5,1,'2008','28.55.44655.8.9.465820','Branco','Voyage II','VAR-6884','SP','São Paulo','1','2013','4650359847-5',3000),
                                          -- CLASSE C --2
--Inserir Valores na Tabela Carro, declarando os @id_Classe,@id_Fabricante e @id_Combustivel como chaves estrangeiras------------------------
(1,3,2,4,'1998','56.63.65465.3.1.846582','Azul Escuro','Ford Focus','FFR-7881','PR','Curitiba','1','2014','6454588465-7',3800),
                                          -- CLASSE C --3
--Inserir Valores na Tabela Carro, declarando os @id_Classe,@id_Fabricante e @id_Combustivel como chaves estrangeiras------------------------
(2,3,4,3,'2007','47.53.46558.8.3.456896','Preto','Renault Sandero','RAS-2684','RJ','Teresópolis','1','2013','6547984646-0',3890),
                                          -- CLASSE D --1
--Inserir Valores na Tabela Carro, declarando os @id_Classe,@id_Fabricante e @id_Combustivel como chaves estrangeiras------------------------
(3,4,3,3,'2005','42.83.98725.4.4.654674','Cinza Chumbo','Fiat Idea','FIR-5883','SP','São Paulo','1','2015','6486847980-6',4000),
                                          -- CLASSE D --2
--Inserir Valores na Tabela Carro, declarando os @id_Classe,@id_Fabricante e @id_Combustivel como chaves estrangeiras------------------------
(4,4,4,1,'2010','63.23.84686.4.6.846866','Cinza Chumbo','Duster Dynamique ','DIY-5882','RJ','Rio de Janeiro','1','2015','4844768464-1',4200),
                                          -- CLASSE E --1
--Inserir Valores na Tabela Carro, declarando os @id_Classe,@id_Fabricante e @id_Combustivel como chaves estrangeiras------------------------
(1,5,4,3,'2009','45.05.41065.1.4.684658','Branco','Renault Fluence Dynamique','RFD-7239','MG','Belo Horizonte','1','2015','4798478947-5',5000),
                                          -- CLASSE E --2
--Inserir Valores na Tabela Carro, declarando os @id_Classe,@id_Fabricante e @id_Combustivel como chaves estrangeiras------------------------
(2,5,2,3,'2005','87.84.05460.9.7.845689','Branco','Ford Fusion','RFD-6139','SP','São Paulo','1','2015','9784654088-7',2300);

SELECT * FROM CARRO;