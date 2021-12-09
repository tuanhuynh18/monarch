class Trips::InvitesController < ApplicationController
  before_action :authenticate_user!
  before_action :set_trip, except: :pending
  before_action :set_invite, only: %i[ show edit update destroy ]

  # GET /trips/:trip_id/invites or /trips/:trip_id/invites.json
  def index
    @invites = @trip.invites
  end

  # GET /trips/:trip_id/invites/1 or /trips/:trip_id/invites/1.json
  def show
  end

  # GET /trips/:trip_id/invites/new
  def new
    @invite = @trip.invites.new
  end

  def pending
    @invites = current_user.received_invites.pending

    respond_to do |format|
        format.json { render :index }
    end
  end

  # GET /trips/:trip_id/invites/1/edit
  def edit
  end

  # POST /trips/:trip_id/invites or /trips/:trip_id/invites.json
  def create
    receiver = User.find_by_email(invite_params[:receiver_email])

    respond_to do |format|
      if receiver.nil?
        format.json { render json: { message: 'User cannot be found.'}, status: :not_acceptable }
      else
        @invite = Invite.new({ sender: current_user, receiver: receiver,
                               trip_id: @trip.id, status: :sent })
        if @invite.save
          format.json { render 'trips/invites/show', status: :created, location: trip_invite_url(@invite) }
        else
          format.json { render json: @invite.errors, status: :unprocessable_entity }
        end
      end
    end
  end

  # PATCH/PUT /trips/:trip_id/invites/1 or /trips/:trip_id/invites/1.json
  def update
    respond_to do |format|
      if !@trip.owner? current_user || @invite.sender != current_user
        format.json { render json: { message: 'Must be trip owner or invitation sender to revoke invitations'}, status: :unauthorized }
      elsif @invite.update(invite_params)
        format.json { render :show, status: :ok, location: trip_invite_url(@trip, @invite) }
      else
        format.json { render json: @invite.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /trips/:trip_id/invites/1 or /trips/:trip_id/invites/1.json
  def destroy
    @invite.destroy
    respond_to do |format|
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_invite
      @invite = Invite.find(params[:id])
    end

  def set_trip
    @trip = current_user.trips.find(params[:trip_id])
  end

    # Only allow a list of trusted parameters through.
    def invite_params
      params.require(:invite).permit(:receiver_email, :status)
    end
end
