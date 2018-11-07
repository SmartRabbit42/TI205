"user strict";

const sha256 = require('js-sha256'),
    fs = require('fs'),
    cors = require('cors'),
    express = require('express'),
    app = express(),
    bodyParser = require('body-parser'),
    porta = 3000,
    sql = require('mssql'),
    conexaoStr = "Server=regulus.cotuca.unicamp.br;Database=PR118175;User Id=PR118175;Password=PR118175;";




sql.connect(conexaoStr).then(conexao => global.conexao = conexao).catch(erro => console.log(erro));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
const rota = express.Router();
app.use('/', rota);
app.listen(porta);
console.log('API Funcionando!');




rota.post('/register', (req, res) => {
    res.header("Access-Control-Allow-Origin", "*");
    const name = req.body.name;
    const age = req.body.age;
    const pass = sha256(req.body.pass);
    var resposta = "ainda indefinido";
    const request = new sql.Request();
    request.query(`SELECT CAST(CASE WHEN EXISTS(SELECT * FROM Usuario WHERE username = '${name}') THEN 1 ELSE 0 END AS BIT) AS re`).then(
        result => {
            if (result.recordset[0].re == true) {
                res.json({ signup: false });
            } else {
                request.query(`INSERT INTO Usuario(username, userage, userpass) VALUES('${name}', '${age}', '${pass}')`);
                res.json({ signup: true });
            }
        }).catch(result => { console.log(result) })
})




rota.post('/login', (req, res) => {
    res.header("Access-Control-Allow-Origin", "*");
    const name = req.body.name;
    const pass = sha256(req.body.pass);
    const request = new sql.Request();
    request.query(`SELECT CAST(CASE WHEN EXISTS(SELECT * FROM Usuario WHERE username = '${name}' AND userpass = '${pass}') THEN 1 ELSE 0 END AS BIT) AS re`).then(
        result => {
            if (result.recordset[0].re == true) {
                res.json({ login: true });
            } else {
                res.json({ login: false });
            }
        }).catch(result => { console.log(result) })
})




rota.post('/media', (req, res) => {
    res.header("Access-Control-Allow-Origin", "*");
    console.log(req.body)
    var form = new formidable.IncomingForm();
    form.parse(req);
    form.on('fileBegin', (name, file) => {
    	file.path = "C:\\Users\\Luiz\\Desktop" + '/uploads/' + file.name;
    	console.log(file.name)
    });
    form.on('file', (name, file) => console.log('Uploaded ' + file.name));
    res.sendStatus(200);
})