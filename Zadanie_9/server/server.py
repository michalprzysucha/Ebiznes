from flask import Flask, request, jsonify
from flask_cors import CORS
import openai

app = Flask(__name__)
CORS(app)

# Poniżej należy podać swój klucz API OpenAI
openai.api_key = ''
msgs = [{"role": "system", "content": "You are a chatbot"}]


@app.route('/', methods=['POST'])
def chat():
    data = request.json
    message = data['message']
    if message:
        msgs.append({"role": "user", "content": message})
    
    response = openai.ChatCompletion.create(
        model="gpt-3.5-turbo", 
        messages=msgs
    )

    generated_text = response.choices[0].message.content

    return jsonify({'response': generated_text})

if __name__ == '__main__':
    app.run()