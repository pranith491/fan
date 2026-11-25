const API = (window.APP_API_URL || "http://localhost:8080") + "/api";
const byId = id => document.getElementById(id);
const formData = form => Object.fromEntries(new FormData(form).entries());
async function request(path, options={}) {
  const response = await fetch(API + path, {credentials:"include", headers:{"Content-Type":"application/json", ...(options.headers||{})}, ...options});
  const data = await response.json().catch(()=>({}));
  if (!response.ok) throw new Error(data.message || data.error || `Request failed (${response.status})`);
  return data;
}
function showStatus(id, message, error=false){const el=byId(id);el.textContent=message;el.style.color=error?"#ff9b8f":"#e6ca8a";}
byId("menuToggle").addEventListener("click",()=>byId("navigation").classList.toggle("open"));
document.querySelectorAll("#navigation a").forEach(a=>a.addEventListener("click",()=>byId("navigation").classList.remove("open")));
byId("bookingForm").addEventListener("submit",async e=>{e.preventDefault();const f=e.currentTarget;const d=formData(f);d.quantity=Number(d.quantity);try{const r=await request("/bookings",{method:"POST",body:JSON.stringify(d)});showStatus("bookingStatus",`Booking request submitted! Booking ID: ${r.id}`);f.reset();}catch(err){showStatus("bookingStatus",err.message,true);}});
byId("messageForm").addEventListener("submit",async e=>{e.preventDefault();const f=e.currentTarget;try{await request("/messages",{method:"POST",body:JSON.stringify(formData(f))});showStatus("messageStatus","Your message was sent successfully.");f.reset();}catch(err){showStatus("messageStatus",err.message,true);}});
byId("registerForm").addEventListener("submit",async e=>{e.preventDefault();try{const r=await request("/auth/register",{method:"POST",body:JSON.stringify(formData(e.currentTarget))});showStatus("registerStatus",r.message||"Account created. You can now log in.");e.currentTarget.reset();}catch(err){showStatus("registerStatus",err.message,true);}});
byId("loginForm").addEventListener("submit",async e=>{e.preventDefault();try{const r=await request("/auth/login",{method:"POST",body:JSON.stringify(formData(e.currentTarget))});showStatus("loginStatus",r.message||"Login successful.");e.currentTarget.reset();}catch(err){showStatus("loginStatus",err.message,true);}});
byId("logoutBtn").addEventListener("click",async()=>{try{const r=await request("/auth/logout",{method:"POST"});alert(r.message||"Logged out.");}catch(err){alert("Logout: "+err.message);}});
