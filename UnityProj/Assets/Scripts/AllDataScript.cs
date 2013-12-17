using UnityEngine;
using System.Collections;

public class AllDataScript : MonoBehaviour {

	
	[HideInInspector]
	public bool isStart, isCredits, isEnd;

	// Use this for initialization
	void Start () {
		isStart = isCredits = isEnd = false;
		isStart = true;
	
		DontDestroyOnLoad (gameObject);
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}
