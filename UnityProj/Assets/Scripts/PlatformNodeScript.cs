using UnityEngine;
using System.Collections;

public class PlatformNodeScript : MonoBehaviour {
	
	void OnDrawGizmos()
	{
		Gizmos.color = new Color(1,0,1);
		Gizmos.DrawSphere (transform.position, 0.5f);
	}
}
