# import math
# import os
# import speech_recognition as sr
# from pydub import AudioSegment
# from pydub.utils import make_chunks
#
# # Set the input and output directories
# input_directory = "mp3_raw"
# output_directory = "lyric"
#
# # Create the output directory if it doesn't exist
# if not os.path.exists(output_directory):
#     os.makedirs(output_directory)
#
# # Define the function to extract lyrics from an audio chunk
# def extract_lyrics(chunk):
#     recognizer = sr.Recognizer()
#     try:
#         with sr.AudioFile(chunk.export(format="wav")) as source:
#             audio_data = recognizer.record(source)
#             lyrics = recognizer.recognize_google(audio_data)
#             return lyrics
#     except sr.UnknownValueError:
#         # Handle unknown value error (no text recognized)
#         return ""
#
# # Process each file in the input directory
# for file_name in os.listdir(input_directory):
#     if file_name.endswith(".mp3"):
#         # Load the audio file
#         audio_path = os.path.join(input_directory, file_name)
#         audio = AudioSegment.from_file(audio_path)
#
#         # Define the desired chunk duration in seconds
#         desired_chunk_duration = 60  # Split audio into 60-second chunks
#
#         # Calculate the chunk length in milliseconds based on the desired duration
#         chunk_length_ms = desired_chunk_duration * 10000
#
#         # Determine the number of chunks based on the audio duration and chunk length
#         total_chunks = math.ceil(audio.duration_seconds / desired_chunk_duration)
#
#         # Split the audio into chunks dynamically
#         chunks = []
#         for i in range(total_chunks):
#             start_time = i * desired_chunk_duration
#             end_time = min((i + 1) * desired_chunk_duration, audio.duration_seconds)
#             chunk = audio[start_time * 1000:end_time * 1000]
#             chunks.append(chunk)
#
#         # Process each chunk and extract the lyrics
#         all_lyrics = ""
#         for chunk in chunks:
#             lyrics = extract_lyrics(chunk)
#             all_lyrics += lyrics + " "  # Add a space between lyrics from different chunks
#
#         # Save the lyrics to a text file
#         output_file_path = os.path.join(output_directory, file_name.replace(".mp3", ".txt"))
#         with open(output_file_path, "w") as output_file:
#             output_file.write(all_lyrics)
#
#         print(f"Lyrics extracted and saved for {file_name}")
#
# print("All files processed successfully.")

import os
import speech_recognition as sr
import langid

input_directory = "mp3_wav_sound"
output_directory = "lyric"

r = sr.Recognizer()

# Iterate over the audio files in the input directory
for file_name in os.listdir(input_directory):
    # Create the input file path
    audio_path = os.path.join(input_directory, file_name)

    # Load the audio file
    with sr.AudioFile(audio_path) as source:
        # Read the audio data
        audio = r.record(source)

        try:
            # Detect the language of the audio
            detected_lang = langid.classify(audio.get_raw_data())[0]

            # Perform speech recognition with the detected language using Bing Speech API
            text = r.recognize_bing(audio, language=detected_lang, key="YOUR_BING_SPEECH_API_KEY")

            # Create the output file path
            output_file_path = os.path.join(output_directory, os.path.splitext(file_name)[0] + ".txt")

            # Save the recognized text to a text file
            with open(output_file_path, "w") as output_file:
                output_file.write(text)

            print(f"Processed {file_name} successfully. Detected language: {detected_lang}")
        except sr.UnknownValueError:
            print(f"Speech recognition could not understand audio in {file_name}.")
        except sr.RequestError as e:
            print(f"Could not request results from the speech recognition service for {file_name}: {e}")


