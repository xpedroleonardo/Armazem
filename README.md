### Armazem | Em desenvolvimento...
<div align="center">

### Controle de TCC
<br/>

<img width="150px" src="./app/src/main/res/drawable-v24/" title="Armazem - Logo" alt="Armazem - Logo">

#### Universidade Cidade de São Paulo

</div>

## 🎥️ Demostração



## 📋️ Introdução

O projeto tem como objetivo disponibilizar recursos para feedback e envio de arquivos de trabalho, possibilitando aos alunos e professores, uma plataforma prática e organizada de envios.

## 🧪 Tecnologias

Esse projeto foi desenvolvido com as seguintes tecnologias:

- [x] Kotlin

## 🚀 Como executar

Clone o projeto e entre na pasta

```bash
➜ git clone https://github.com/xpedroleonardo/Armazem.git
```

Após isso você deverá acessar o arquivo <b>src/controle_tcc.sql</b>, nele estão os scripts para criar o banco que dados, para que o projeto funcione corretamente.

Após a criação do banco de dados você deverá acessar o arquivo <b>src/Config.php</b>, e altere os campos relacionados ao banco de dados, conforme está configurado na sua máquina.

```php

/** BASE URL */
define("ROOT", "http://localhost/Controle-TCC"); // Url do projeto no seu servidor local.

/** DATABASE CONNECT */
define("DATA_LAYER_CONFIG", [
  "driver" => "mysql", //Drive de Conexão
  "host" => "localhost", //Host
  "port" => "3306", //Porta de Conexão
  "dbname" => "controle_tcc", //Nome do Banco de Dados
  "username" => "root", //Usuário do Banco de Dados
  "passwd" => "", //Senha do Banco de Dados
  ...
]);

```


### 👨‍💻️👩‍💻️ Autores

| [<img src="https://avatars.githubusercontent.com/u/111441163?v=4" width='62px' title="Camille Ruiz">](https://github.com/camizru) <br> Camille | [<img src="https://avatars.githubusercontent.com/u/83608323?v=4" width='62px' title="Heloisa Romão">](https://github.com/HeloisaRomao) <br> Heloisa | [<img src="https://avatars.githubusercontent.com/u/67611596?v=4" width='62px' title="Keila Sales">](https://github.com/KeilaS06) <br> Keila | [<img src="https://avatars.githubusercontent.com/u/111459788?v=4" width='62px' title="Kethellen Morais">](https://github.com/kethellenmorais)<br> Kethellen | [<img src="https://avatars.githubusercontent.com/u/112582501?v=4" width='62px' title="Luana Santana">](https://github.com/santanaluana)<br> Luana| [<img src="https://avatars.githubusercontent.com/u/112817731?v=4" width='62px' title="Mateus Ferreira">](https://github.com/MafdSantana)<br>Mateus | [<img src="https://avatars.githubusercontent.com/u/50972494?v=4" width='62px' title="Pedro Leonardo">](https://github.com/xpedroleonardo) <br> Pedro |
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |

## 📝 License

Esse projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

Gostou do projeto ? Dê uma estrela ⭐
