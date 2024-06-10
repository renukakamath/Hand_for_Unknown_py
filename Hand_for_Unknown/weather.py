import requests

def get_weather(city):
    api_key = "5fa7acf7a508491fbf9f3c6411bbb378"  # Replace with your actual Weatherbit API key
    url = f"https://api.weatherbit.io/v2.0/forecast/daily?city={city}&key={api_key}"

    try:
        response = requests.get(url)
        response.raise_for_status()  # Check if the request was successful

        data = response.json()

        # Extract weather information from the first forecast day
        temperature = data["data"][0]["temp"]
        humidity = data["data"][0]["rh"]
        precipitation = data["data"][0].get("precip", 0)
        rainfall = data["data"][0].get("precip", 0)
        weather = data["data"][0]["weather"]["description"]

        # Print the weather report
        print("Weather in", city)
        print("Temperature:", temperature, "°C")
        print("Humidity:", humidity, "%")
        print("Precipitation:", precipitation, "mm")
        print("Rainfall:", rainfall, "mm")
        print("Weather:", weather)

    except requests.exceptions.RequestException as e:
        print("Error occurred:", e)

city_name = input("Enter the name of the city: ")
get_weather(city_name)
