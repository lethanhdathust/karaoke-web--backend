import os
import moviepy.editor
from tkinter.filedialog import *




# Input folder path for videos
input_folder = "video"

# Output folder path for audio files
output_folder = "sound"

# Create the output folder if it doesn't exist
if not os.path.exists(output_folder):
    os.makedirs(output_folder)

#Get the output folder to save audio files
video_files = [file for file in os.listdir(input_folder)  if file.endswith('.mp4')]

# Iterate over the video files
for video_file in video_files:
    video_path = os.path.join(input_folder, video_file)
    audio_file = os.path.splitext(video_file)[0] + ".mp3"
    audio_path = os.path.join(output_folder, audio_file)

    # Convert video to audio
    video = moviepy.editor.VideoFileClip(video_path)
    audio = video.audio
    audio.write_audiofile(audio_path)

    print(f"Converted {video_file} to {audio_file}")

print("Conversion completed")