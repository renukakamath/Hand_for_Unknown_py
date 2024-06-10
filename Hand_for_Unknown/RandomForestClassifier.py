def predicts(cc):
    from sklearn.ensemble import RandomForestClassifier
    from sklearn.model_selection import train_test_split
    from sklearn.metrics import accuracy_score

    # Assuming you have a CSV file with the training data
    # Load the CSV file and split it into features (X) and target (y)
    import pandas as pd
    data = pd.read_csv('question.csv')
    X = data.drop('label', axis=1)  # Remove the target column from features
    y = data['label']  # Select the target column

    # Split the data into training and testing sets
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

    # Create a Random Forest classifier with default parameters
    rf_classifier = RandomForestClassifier()

    # Train the classifier using the training data
    rf_classifier.fit(X_train, y_train)

    # Make predictions on the test data
    y_pred = rf_classifier.predict(X_test)

    # Calculate the accuracy of the model
    accuracy = accuracy_score(y_test, y_pred)
    print("Accuracy:", accuracy)

    out=rf_classifier.predict(cc)
    return out
