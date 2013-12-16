using UnityEngine;
using System.Collections;

public class MusicScript : MonoBehaviour {

	public AudioClip mainLoop;

	// Use this for initialization
	void Start () {
		Invoke ("onIntroEnd", audio.clip.length);
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	void onIntroEnd() {
		audio.clip = mainLoop;
		audio.loop = true;
		audio.Play ();
	}
}
