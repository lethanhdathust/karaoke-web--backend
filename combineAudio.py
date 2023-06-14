import os
import re
from pydub import AudioSegment

def combine_matching_audio_files(folder1, folder2, output_folder):
    files1 = get_audio_files(folder1)
    files2 = get_audio_files(folder2)

    # Iterate through files in folder1 and folder2
    for file1 in files1:
        for file2 in files2:
            # Check if file names match
            if check_audio_file_match(file1, file2):
                file1_path = os.path.join(folder1, file1)
                file2_path = os.path.join(folder2, file2)

                audio1 = AudioSegment.from_file(file1_path)
                audio2 = AudioSegment.from_file(file2_path)

                combined_audio = audio1 + audio2

                # Create the output folder if it doesn't exist
                os.makedirs(output_folder, exist_ok=True)

                output_filename = os.path.splitext(file1)[0] + "_combined.wav"
                output_path = os.path.join(output_folder, output_filename)

                combined_audio.export(output_path, format='wav')

                print(f"Audio files '{file1}' and '{file2}' combined and saved successfully.")

def get_audio_files(folder):
    audio_files = []
    for file in os.listdir(folder):
        if file.endswith(".wav"):
            audio_files.append(file)
    return audio_files



def check_audio_file_match(file1_name, file2_name):
    # Remove file extension
    file1_name = os.path.splitext(file1_name)[0]
    file2_name = os.path.splitext(file2_name)[0]

    # Remove common patterns like "audio", "vocal", etc.
    patterns_to_remove = ['audio', 'vocal']  # Add more patterns if needed

    for pattern in patterns_to_remove:
        file1_name = re.sub(pattern, '', file1_name, flags=re.IGNORECASE)
        file2_name = re.sub(pattern, '', file2_name, flags=re.IGNORECASE)

    # Remove non-alphanumeric characters
    file1_name = re.sub(r'\W+', '', file1_name)
    file2_name = re.sub(r'\W+', '', file2_name)

    # Perform case-insensitive string comparison
    return file1_name.lower() == file2_name.lower()

# Example usage
folder1 = 'VideotoAudioPython/wav_sound'
folder2 = 'save_records'
output_folder = 'combinerecord'

# Create the output folder if it doesn't exist
if not os.path.exists(output_folder):
    os.makedirs(output_folder)

combine_matching_audio_files(folder1, folder2, output_folder)
