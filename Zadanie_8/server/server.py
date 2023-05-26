from flask import Flask, request, jsonify
from werkzeug.exceptions import NotFound
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

users = {"Michal": "1234"}

@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    username = data['username']
    password = data['password']


    if username in users and users[username] == password:
        return '', 200
    else:
        return '', 401

@app.route('/register', methods=['POST'])
def register():
    data = request.get_json()
    username = data['username']
    password = data['password']

    if username in users:
        return '', 409
    else:
        users[username] = password
        return '', 200

@app.errorhandler(NotFound)
def page_not_found(error):
    return '', 404

if __name__ == '__main__':
    app.run()