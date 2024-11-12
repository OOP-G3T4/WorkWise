import express from 'express';
import bodyParser from 'body-parser';
import cors from 'cors';
import twilio from 'twilio';
import dotenv from 'dotenv';

dotenv.config();

const app = express();
app.use(cors());
app.use(bodyParser.json());

const accountSid = process.env.VITE_TWILIO_ACCOUNT_SID;
const authToken = process.env.VITE_TWILIO_AUTH_TOKEN;
const from = process.env.VITE_TWILIO_FROM_NUMBER;
const to = process.env.VITE_TWILIO_TO_NUMBER;

const client = twilio(accountSid, authToken);

app.post('/send-message', (req, res) => {
    const { date, time } = req.body;
  
    client.messages
      .create({
        from: `whatsapp:${from}`,
        to: `whatsapp:${to}`,
        body: `🔔 *Schedule Update*\n\nYour work schedule has been updated. Your new appointment is confirmed for ${date} at ${time}. If you have any questions or need further details, please feel free to reach out.`,
      })
      .then((message) => res.status(200).send({ sid: message.sid }))
      .catch((error) => res.status(500).send({ error: error.message }));
  });
  
  const PORT = process.env.PORT || 3000;
  app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
  });