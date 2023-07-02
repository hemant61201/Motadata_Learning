package Ping

type DiscoveryRequest struct {
	Operation         string `json:"Operation"`
	CredentialProfile struct {
		Username []string `json:"username"`
		Password []string `json:"password"`
	} `json:"credentialProfile"`
	DiscoveryProfile struct {
		IP   []string `json:"ip"`
		Port int      `json:"port"`
		IDs  []int    `json:"id"`
	} `json:"discoveryProfile"`
}
