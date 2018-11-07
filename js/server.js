var express = require('express');
var app = express();
var http = require('http').Server(app);
var cors = require('cors');
var sha256 = require('js-sha256');
var bodyParser = require('body-parser');
var sql = require('mssql');
var outrosql = require('mssql');
var io = require('socket.io')(http);



var clients = [];
var rooms = [{
  name: "global",
  pass: ""
}];
var roomClient = [];

app.use(cors());




app.use(bodyParser.urlencoded({
  extended: true
}));
app.use(bodyParser.json());
const rota = express.Router();
app.use('/', rota);

app.get('/', function(req, res) {
  res.send('Servidor Ativo');
});
//Select nome, senha from Sala;      Select sala, usario from SalaUsuario
http.listen(3000, async function() {
  console.log("Servidor rodando na porta: 3000");
  await sql.connect("Server=regulus.cotuca.unicamp.br;Database=PR118175;User Id=PR118175;Password=PR118175");
  const request = new sql.Request();
  request.query("Select userName as userName from Usuario").then(result => {
      for (var i = 0; i < result.recordsets.length; i++) {
        console.log(result.recordsets.length);
        console.log(result.recordsets[0][i].userName);
        //    rooms[i] = {
        //      name: result.recordset[0][i].nome,
        //      pass: result.recordset[0][i].senha
        //    };
        clients[i] = result.recordsets[0][i].userName;
        //    roomClient[i] = {
        //      name: clients[result.recordset[2].usuario],
        //      room: rooms[result.recordset[2].sala].name
      }
      console.log(rooms + "\n \n" + clients + "\n \n" + roomClient);
    }

  ).catch(result => {
    console.log(result);
  });
});





io.on("connection", function(client) {
  console.log('usuario conectado');
  client.on("login", function(username) {
    clients[client.id] = username;
  });
  client.on("join", function(data) {
    var roomID = roomExists(data.name);
    if (roomID != -1) {
      if (rooms[roomID].pass == data.pass) {
        client.join(data.name);
        client.emit("join", data.name);
        console.log(clients[client.id] + " entrou na sala " + data.name);
        client.emit("correctPass", {
          response: true,
          id: data.name
        });
        client.emit("joined", {
          msg: "Você se conectou à sala",
          room: data.name
        });
        client.broadcast.to(data.name).emit("joined", {
          msg: clients[client.id] + " se conectou à sala",
          room: data.name
        });
        roomClient[roomClient.length] = {
          room: data.name,
          name: clients[client.id]
        };
      } else
        client.emit("correctPass", {
          response: false,
          id: data.name
        });
    }
  });
  client.on("msg", function(data) {
    if (roomExists(data.room) != -1) {
      client.broadcast.to(data.room).emit("msg", data);
      const request = new sql.Request();
      request.query(`SELECT Sala.id AS salaId FROM Sala WHERE Sala.nome = '${data.room}'`).then(
        result => {
          request.query(`INSERT INTO Mensagem(sala, conteudo) VALUES ('${result.recordset[0].salaId}', '${data.msg}')`);
        }
      ).catch(result => {
        console.log(result)
      })
      console.log(client.id);
    }

  });
  client.on("create", function(data) {
    if (roomExists(data.name) == -1) {
      rooms[rooms.length] = {
        name: data.name,
        pass: sha256(data.pass)
      };

      const request = new sql.Request();
      request.query(`INSERT INTO Sala(nome, senha) VALUES('${data.name}','${sha256(data.pass)}')`).then().catch(result => console.log(result));

      client.join(data.name);
      client.emit("create", data.name);
      console.log("Usuario criou a sala " + data.name);
      roomClient[roomClient.length] = {
        room: data.name,
        name: clients[client.id]
      };

      if (data.pass == "")
        client.broadcast.emit("created", {
          user: clients[client.id],
          name: data.name,
          pass: false
        });
      else
        client.broadcast.emit("created", {
          user: clients[client.id],
          name: data.name,
          pass: true
        });
    } else
      client.emit("roomExist", true);
  });

  client.on("logar", function(data) {
    const name = data.name;
    const pass = sha256(data.pass);
    const request = new sql.Request();
    request.query(`SELECT CAST(CASE WHEN EXISTS(SELECT * FROM Usuario WHERE username = '${name}' AND userpass = '${pass}') THEN 1 ELSE 0 END AS BIT) AS re`).then(
      result => {
        if (result.recordset[0].re == true) {
          client.emit("login", true);
          for (var i = 0; i < roomClient.length; i++) {
            console.log(client.id);
            if (roomClient[i].name == name) {
              client.join(roomClient[i].room);
              client.emit("joinar", roomClient[i].room);
            }
          }
        } else {
          client.emit("login", false);
        }
      }).catch(result => {
      console.log(result)
    })
  });
})

function roomExists(room) {
  for (var i = 0; i < rooms.length; i++)
    if (rooms[i].name == room)
      return i;
  return -1;
}

rota.post('/register', (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  const name = req.body.name;
  const age = req.body.age;
  const pass = sha256(req.body.pass);
  const request = new sql.Request();
  request.query(`SELECT CAST(CASE WHEN EXISTS(SELECT * FROM Usuario WHERE username = '${name}') THEN 1 ELSE 0 END AS BIT) AS re`).then(
    result => {
      if (result.recordset[0].re == true) {
        res.json({
          signup: false
        });
      } else {
        request.query(`INSERT INTO Usuario(username, userage, userpass) VALUES('${name}', '${age}', '${pass}')`);
        res.json({
          signup: true
        });
      }
    }).catch(result => {
    console.log(result)
  })
})



/*rota.post('/media', (req, res) => {
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
})*/