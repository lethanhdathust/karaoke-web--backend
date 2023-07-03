from pydub import AudioSegment
import os

# Set the input and output folder paths
# input_folder = "sound"
# output_folder = "wav_sound"


input_folder = "mp3_raw"
output_folder = "mp3_wav_sound"

# Create the output folder if it doesn't exist
if not os.path.exists(output_folder):
    os.makedirs(output_folder)

# Loop through the input folder
for filename in os.listdir(input_folder):
    if filename.endswith(".mp3"):
        # Set the input and output file paths
        input_file = os.path.join(input_folder, filename)
        output_file = os.path.join(output_folder, filename.replace(".mp3", ".wav"))

        # Load the MP3 file
        mp3_file = AudioSegment.from_mp3(input_file)

        # Export the MP3 file to WAV format
        mp3_file.export(output_file, format="wav")

        print(f"{filename} converted successfully!")