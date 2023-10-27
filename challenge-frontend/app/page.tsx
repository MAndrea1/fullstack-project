
export default function Home() {
  return (
    <main className="container mx-auto px-4 py-5">
      <div className='flex items-center justify-between'>
        <h1 className='font-bold text-3xl text-[#f09e3a] my-5'>Ensolvers Challenge</h1>
        <div className='flex flex-col-reverse space-y- md:space-y-0 md:flex-row md:items-center md:space-x-2'>
          <select className="select select-sm select-ghost select-warning w-full max-w-xs">
            <option disabled selected>Filter</option>
            <option>Category1</option>
            <option>Category2</option>
            <option>Category3</option>
          </select>
          <button className="btn btn-sm btn-outline btn-warning">Add new task</button>
        </div>
      </div>
      <div className="overflow-x-auto">
        <table className="table">
          {/* head */}
          <thead>
            <tr>
              <th>Title</th>
              <th>Description</th>
              <th>Category</th>
              <th>Complete?</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {/* row 1 */}
            <tr>
              <td><p className='font-bold'>What is the title is super long??? then what should be done????</p></td>
              <td><p>what is the text is super long and I'm not able to contain it in a column because of how long it is????</p></td>
              <td><p>Category1</p></td>
              <td>
                <div className='flex justify-center'>
                  <input type="checkbox" checked={true} className="checkbox" />
                </div>
              </td>
              <td>
                <div className='flex flex-col space-y-1 md:flex-row md:space-y-0 md:space-x-1'>
                  <button className="btn btn-sm btn-outline btn-info">Edit</button>
                  <button className="btn btn-sm btn-outline btn-error">Delete</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </main>
  )
}
