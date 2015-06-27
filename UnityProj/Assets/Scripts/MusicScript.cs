using UnityEngine;
using System.Collections;

public class MusicScript : MonoBehaviour {

	public AudioClip mainLoop;

	// Use this for initialization
	void Start () {
		Invoke ("onIntroEnd", GetComponent<AudioSource>().clip.length);
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	void onIntroEnd() {
		GetComponent<AudioSource>().clip = mainLoop;
		GetComponent<AudioSource>().loop = true;
		GetComponent<AudioSource>().Play ();
	}
}
