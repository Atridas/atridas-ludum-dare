using UnityEngine;
using System.Collections;

public class PlatformScript : MonoBehaviour {

	public Transform node1, node2;
	public float timeOnFirstNode, timeOnSecondNode, transitionTime;

	private enum State {
		ON_FIRST, TO_SECOND, ON_SECOND, TO_FIRST
	}
	private float time;
	private State state;

	// Use this for initialization
	void Start () {
		time = 0;
		state = State.ON_FIRST;
	}
	
	// Update is called once per frame
	void Update () {
		time += Time.deltaTime;

		switch(state) {
		case State.ON_FIRST:
		{
			if(time > timeOnFirstNode) {
				time = 0;
				state = State.TO_SECOND;
			}
		}break;
		case State.TO_SECOND:
		{
			if(time > transitionTime) {
				time = 0;
				state = State.ON_SECOND;
				transform.position = node2.position;
			} else {
				float alpha = time / transitionTime;
				transform.position = node2.position * alpha + node1.position * (1 - alpha);
			}
		}break;
		case State.ON_SECOND:
		{
			if(time > timeOnSecondNode) {
				time = 0;
				state = State.TO_FIRST;
			}
		}break;
		case State.TO_FIRST:
		{
			if(time > transitionTime) {
				time = 0;
				state = State.ON_FIRST;
				transform.position = node1.position;
			} else {
				float alpha = time / transitionTime;
				transform.position = node1.position * alpha + node2.position * (1 - alpha);
			}
		}break;
		}
	}
	
	void OnDrawGizmos()
	{
		Gizmos.color = Color.red;
		Gizmos.DrawLine(node1.position, node2.position);
	}
}
