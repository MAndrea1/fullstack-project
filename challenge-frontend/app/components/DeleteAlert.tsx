import { Todo } from "@/typings"
import { getEndpoint } from "@/config-endpoints";

const { basetodosUrl } = getEndpoint();

type params = {
  todo: Todo
  setIsDeleted: React.Dispatch<React.SetStateAction<boolean>>    
  setToDelete: React.Dispatch<React.SetStateAction<boolean>>    
}

export const DeleteAlert = ({ todo, setIsDeleted, setToDelete }: params ) => {
  const todosDeleteUrl = basetodosUrl + "todo/" + todo.id   

  const deleteUser = async(id: number) => {
    const requestOptions = {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      }
    };
    try {
      const response = await fetch(todosDeleteUrl, requestOptions);

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
    } catch (error) {
      console.error('Error:', error);
    }
    
    setToDelete(false)    
    setIsDeleted(true)
  }
  
  return(
    <div className="toast toast-start">
    <div className="alert alert-warning">
    <svg xmlns="http://www.w3.org/2000/svg" className="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" /></svg>
      <span>Are you sure you want to delete this task?</span>
      <div>
        <button onClick={() => setToDelete(false)} className="btn btn-sm mr-2">Cancel</button>
        <button onClick={() => deleteUser(todo.id)} className="btn btn-sm">Confirm</button>
      </div>
    </div>
  </div>    
  )  
}