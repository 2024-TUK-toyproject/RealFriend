# base image
FROM python:3.9

# set work directory
WORKDIR /usr/src/app

# copy source code to work directory
COPY . .

# install dependencies
RUN pip install --no-cache-dir -r requirements.txt

# command to run on container start
CMD ["uvicorn", "run:app", "--host", "0.0.0.0", "--port", "5000"]
