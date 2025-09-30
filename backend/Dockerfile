FROM python:3.11-slim

WORKDIR /app
COPY ./app /app
COPY requirements.txt .

ENV PYTHONPATH=/app

RUN pip install -r requirements.txt

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]
	